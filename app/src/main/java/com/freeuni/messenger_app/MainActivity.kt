package com.freeuni.messenger_app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel
import com.freeuni.messenger_app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
  private lateinit var auth: FirebaseAuth
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val viewModel = ViewModelProvider(
      this, ViewModelProvider.AndroidViewModelFactory
        .getInstance(application)
    ).get(AuthViewModel::class.java)

    viewModel.userData.observe(this) {
      Toast.makeText(this, it.email, Toast.LENGTH_SHORT).show()
    }

    auth = FirebaseAuth.getInstance()

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.register.setOnClickListener {
      val nickname = binding.nicknameField.text
      val password = binding.passwordField.text

      if (nickname.isEmpty()) {
        Toast.makeText(this, "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (password.isEmpty()) {
        Toast.makeText(this, "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      viewModel.register(nickname.toString(), password.toString())
    }

    binding.login.setOnClickListener {
      val nickname = binding.nicknameField.text
      val password = binding.passwordField.text

      if (nickname.isEmpty()) {
        Toast.makeText(this, "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (password.isEmpty()) {
        Toast.makeText(this, "Nickname is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      viewModel.signIn(nickname.toString(), password.toString())
    }

  }
}