package com.freeuni.messenger_app.activities.auth

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.databinding.FragmentSignInBinding
import com.freeuni.messenger_app.viewmodels.AuthViewModel


class SignInFragment : Fragment() {
  private lateinit var binding: FragmentSignInBinding
  private lateinit var navController: NavController
  private val viewModel: AuthViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentSignInBinding.inflate(inflater, container, false)

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

    return binding.root
  }
}