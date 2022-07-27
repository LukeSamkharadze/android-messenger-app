package com.freeuni.messenger_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAuthBinding
  private lateinit var viewModel: AuthViewModel
  private lateinit var navControler: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityAuthBinding.inflate(layoutInflater)
    setContentView(binding.root)

//    navControler = Navigation.findNavController(binding.root)

    val navHostFragment = supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment
    navControler = navHostFragment.navController

    viewModel = ViewModelProvider(
      this, ViewModelProvider.AndroidViewModelFactory
        .getInstance(application)
    ).get(AuthViewModel::class.java)
//
    viewModel.user.observe(this) {
      navControler.navigate(R.id.signInFragment)
    }
  }
}