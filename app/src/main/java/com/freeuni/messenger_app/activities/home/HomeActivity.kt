package com.freeuni.messenger_app.activities.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.activities.auth.AuthActivity
import com.freeuni.messenger_app.databinding.MainPageBinding
import com.freeuni.messenger_app.viewmodels.HomeViewModel
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
  private lateinit var binding: MainPageBinding
  private lateinit var viewModel: HomeViewModel
  private lateinit var navControler: NavController

  private var imageUri: Uri? = null

  private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    println("Handle $exception in CoroutineExceptionHandler")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = MainPageBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val navHostFragment =
      supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment
    navControler = navHostFragment.navController

    binding.bottomNavigationView.setupWithNavController(navControler)

    viewModel =
      ViewModelProvider(this)[HomeViewModel::
      class.java]

    viewModel.userFirebaseLiveData.observe(this) {
      if (it == null) {
        finish()
        startActivity(Intent(this, AuthActivity::class.java))
      }
    }

    return

//    val navHostFragment =
//      supportFragmentManager.findFragmentById(R.id.authNavHostFragment) as NavHostFragment
//    navControler = navHostFragment.navController


//    binding.signOutButton.setOnClickListener {
//      viewModel.signOut()
//    }
//
//    viewModel.user.observe(this) {
//      if (it == null) {
//        finish()
//        startActivity(Intent(this, AuthActivity::class.java))
//      }
//    }
//
//    CoroutineScope(Dispatchers.IO).launch {
//      val searchedUsers = viewModel.searchUsers("taz")
//    }
//
//
//    viewModel.user.observe(this) { user ->
//      if (user != null) {
//        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
//          val profileUrl = viewModel.getProfilePicUrl(user.uid)
//
//          withContext(Dispatchers.Main) {
//            Glide.with(binding.root).load(profileUrl).into(binding.profilePicture)
//          }
//        }
//      }
//    }
//
//    binding.selectImageButton.setOnClickListener {
//      val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)
//      startActivityForResult(intent, 100)
//    }
//
//    binding.uploadImageButton.setOnClickListener {
//      if (imageUri != null) {
//        viewModel.uploadProfile(imageUri!!).addOnSuccessListener {
//          // TODO: save user info
//          Toast.makeText(this, "Sucesfully updated", Toast.LENGTH_SHORT).show()
//        }
//      }
//    }
//  }
//


//  @Deprecated("Deprecated in Java")
//  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    super.onActivityResult(requestCode, resultCode, data)
//
//    if (requestCode == 100 && resultCode == RESULT_OK) {
//      imageUri = data?.data
//      binding.profilePicture.setImageURI(imageUri)
//    }
  }
}