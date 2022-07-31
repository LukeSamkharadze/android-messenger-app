package com.freeuni.messenger_app.activities.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.freeuni.messenger_app.viewmodels.AuthViewModel
import com.freeuni.messenger_app.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
  private lateinit var binding: FragmentSignUpBinding
  private lateinit var viewModel: AuthViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    binding = FragmentSignUpBinding.inflate(inflater, container, false)

    viewModel =
      ViewModelProvider(requireActivity())[AuthViewModel::class.java]

    binding.registerButton.setOnClickListener {
      val email = binding.emailEditText.text.toString()
      val password = binding.passwordEditText.text.toString()
      val bio = binding.bioEditText.text.toString()

      if (email.isEmpty()) {
        Toast.makeText(requireContext(), "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (password.isEmpty()) {
        Toast.makeText(requireContext(), "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      viewModel.register(email, password).onSuccessTask {
        viewModel.saveUser(it.user!!.uid, email, bio)
      }
    }

    return binding.root
  }
}