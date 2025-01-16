package com.putragandad.regres.ui.thirdscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.putragandad.regres.databinding.FragmentThirdScreenBinding
import com.putragandad.regres.ui.thirdscreen.adapter.ListUserAdapter
import com.putragandad.regres.ui.thirdscreen.adapter.ListUserItemLoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ThirdScreenFragment : Fragment() {
    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel : ThirdScreenViewModel by viewModels()
    private val adapter = ListUserAdapter { selectedData ->
        // send back the result
        findNavController().previousBackStackEntry?.savedStateHandle?.set("username", "${selectedData.firstName} ${selectedData.lastName}")
        findNavController().popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpSwipeRefresh()
        observer()
        onClickListener()
    }

    private fun observer() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userResultState.collectLatest { state ->
                    adapter.submitData(state.data) // set data to adapter

                    // detect internet connection
                    if(!state.isOffline) {
                        binding.pullToRefreshRV.visibility = View.VISIBLE
                        binding.viewNointernet.root.visibility = View.GONE
                    } else {
                        binding.pullToRefreshRV.visibility = View.GONE
                        binding.viewNointernet.root.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvUserData.adapter = adapter
        binding.rvUserData.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvUserData.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ListUserItemLoadingStateAdapter { adapter.retry() },
            footer = ListUserItemLoadingStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { state ->
            when(state.source.refresh) {
                is LoadState.NotLoading -> {
                    // detect initial load
                    if (state.append.endOfPaginationReached && adapter.itemCount < 1) {
                        binding.pullToRefreshRV.isRefreshing = true
                    } else {
                        binding.pullToRefreshRV.isRefreshing = false
                    }
                }

                is LoadState.Loading -> {
                    if (adapter.itemCount == 0) {
                        binding.pullToRefreshRV.isRefreshing = true
                    } else {
                        binding.pullToRefreshRV.isRefreshing = false
                    }
                }

                else -> { }
            }
        }
    }

    private fun setUpSwipeRefresh() {
        binding.pullToRefreshRV.setOnRefreshListener {
            refreshUserList()
        }
    }

    private fun onClickListener() {
        binding.viewNointernet.btnTryAgain.setOnClickListener {
            refreshUserList()
        }

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun refreshUserList() {
        viewModel.cleanUserList()
        viewModel.getListUser()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}