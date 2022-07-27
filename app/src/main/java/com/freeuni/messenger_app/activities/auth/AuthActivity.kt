package com.freeuni.messenger_app.activities.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.activities.home.HomeActivity
import com.freeuni.messenger_app.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
  private lateinit var binding: ActivityAuthBinding
  private lateinit var viewModel: AuthViewModel
  private lateinit var navControler: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityAuthBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment
    navControler = navHostFragment.navController

    viewModel =
      ViewModelProvider(this)[AuthViewModel::class.java]

    viewModel = ViewModelProvider(
      this, ViewModelProvider.AndroidViewModelFactory
        .getInstance(application)
    )[AuthViewModel::class.java]

//    viewModel.user.observe(this) {
//      if (it != null) {
//        finish()
//        startActivity(Intent(this, HomeActivity::class.java))
//      }
//    }
  }
}