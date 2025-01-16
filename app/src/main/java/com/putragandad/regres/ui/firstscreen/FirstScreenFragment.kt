package com.putragandad.regres.ui.firstscreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.putragandad.regres.R
import com.putragandad.regres.databinding.FragmentFirstScreenBinding
import com.putragandad.regres.ui.secondscreen.SecondScreenFragmentDirections

class FirstScreenFragment : Fragment() {
    private var _binding: FragmentFirstScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel : FirstScreenViewModel by viewModels()

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
        setProfilePhotoObserver()
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
                Toast.makeText(requireActivity(), getString(R.string.name_column_required_error), Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCheckPalindrome.setOnClickListener {
            val text = binding.etPalindrome.editText?.text.toString()
            if(!text.isNullOrEmpty()) {
                dialogPalindromeResult(viewModel.checkPalindrome(text), text)
            }
        }
    }

    private fun dialogPalindromeResult(result: Boolean, text: String) {
        MaterialAlertDialogBuilder(requireActivity())
            .setTitle(getString(R.string.palindrome_check_result_dialog_title))
            .setMessage("Palindrome Check for $text = ${getString(if (result) R.string.ispalindrome_result_text_dialog else R.string.notpalindrome_result_text_dialog)}")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setProfilePhotoObserver() {
        val pickMedia = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedPicUri = result.data?.data
                Glide.with(requireView())
                    .load(selectedPicUri)
                    .into(binding.icBtnSelectPhoto)
            }
        }

        binding.icBtnSelectPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            pickMedia.launch(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}