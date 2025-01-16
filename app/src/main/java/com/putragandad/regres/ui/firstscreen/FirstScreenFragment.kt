package com.putragandad.regres.ui.firstscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.putragandad.regres.R
import com.putragandad.regres.databinding.FragmentFirstScreenBinding
import com.putragandad.regres.ui.secondscreen.SecondScreenFragmentDirections

class FirstScreenFragment : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClickListener()
    }

    private fun onClickListener() {
        binding.btnNext.setOnClickListener {
            val text = binding.etName.editText?.text.toString()
            if(!text.isNullOrEmpty()) { //check text null/not
                if (findNavController().currentDestination?.id == R.id.firstScreenFragment) {
                    val action = FirstScreenFragmentDirections.actionFirstScreenFragmentToSecondScreenFragment(username = text)
                    findNavController().navigate(action) // send data with safe args
                }
            } else {
                Toast.makeText(requireActivity(), "Name field can't be empty!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}