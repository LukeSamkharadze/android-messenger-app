package com.freeuni.messenger_app.activities.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.codingstuff.loginsignupmvvm.viewmodel.AuthViewModel
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.activities.auth.AuthActivity
import com.freeuni.messenger_app.databinding.ActivityAuthBinding
import com.freeuni.messenger_app.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
  private lateinit var binding: ActivityHomeBinding
  private lateinit var viewModel: AuthViewModel
//  private lateinit var navControler: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityHomeBinding.inflate(layoutInflater)
    setContentView(binding.root)

//    val navHostFragment =
//      supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment
//    navControler = navHostFragment.navController

//    viewModel = ViewModelProvider(
//      this, ViewModelProvider.AndroidViewModelFactory
//        .getInstance(application)
//    )[AuthViewModel::class.java]
//
//    binding.signOutButton.setOnClickListener {
//      viewModel.signOut()
//      finish()
//      startActivity(Intent(this, AuthActivity::class.java))
//    }
//
//    viewModel.user.observe(this) {
//      if (it == null) {
//        finish()
//        startActivity(Intent(this, HomeActivity::class.java))
//      }
//    }
  }
}