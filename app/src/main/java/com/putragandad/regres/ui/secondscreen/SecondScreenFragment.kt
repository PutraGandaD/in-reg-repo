package com.putragandad.regres.ui.secondscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.putragandad.regres.R
import com.putragandad.regres.databinding.FragmentFirstScreenBinding
import com.putragandad.regres.databinding.FragmentSecondScreenBinding

class SecondScreenFragment : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding!!

    private val args : SecondScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListener()
        observer()
    }

    private fun observer() {
        // observe user name from reqres in third screen
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("username")?.observe(
            viewLifecycleOwner) { result ->
            binding.tvUserName.text = result
        }

        // observe name from first screen (safe args)
        val argument = args.username
        binding.tvName.text = argument.toString()
    }

    private fun onClickListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNext.setOnClickListener {
            findNavController().navigate(R.id.action_secondScreenFragment_to_thirdScreenFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}