package com.freeuni.messenger_app.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
  private lateinit var binding: FragmentSignInBinding
  private lateinit var viewModel: AuthViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    // Inflate the layout for this fragment
    binding = FragmentSignInBinding.inflate(inflater, container, false)

    viewModel = ViewModelProvider(
      this, ViewModelProvider.AndroidViewModelFactory
        .getInstance(requireActivity().application)
    ).get(AuthViewModel::class.java)

    binding.registerButton.setOnClickListener {
      findNavController().navigate(R.id.signUpFragment)
    }

    binding.loginButton.setOnClickListener {
      val email = binding.emailEditText.text.toString()
      val password = binding.passwordEditText.text.toString()

      if (email.isEmpty()) {
        Toast.makeText(requireContext(), "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (password.isEmpty()) {
        Toast.makeText(requireContext(), "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      viewModel.signIn(email, password)
    }

    viewModel.user.observe(requireActivity()) {
      Log.d("app", it.toString())
    }

    return binding.root
  }
}