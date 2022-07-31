package com.freeuni.messenger_app.activities.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.freeuni.messenger_app.databinding.ProfilePageBinding
import com.freeuni.messenger_app.viewmodels.AuthViewModel
import com.freeuni.messenger_app.viewmodels.HomeViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class ProfileFragment : Fragment() {
  private lateinit var binding: ProfilePageBinding
  private var imageUri: Uri? = null
  private val viewModel: HomeViewModel by activityViewModels()

  private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = ProfilePageBinding.inflate(inflater, container, false)

    viewModel.userLiveData.observe(viewLifecycleOwner) { user ->
      if (user != null) {
        binding.emailEditText.setText(user.email)
        binding.bioEditText.setText(user.bio)

        CoroutineScope(Dispatchers.IO).launch(coroutineExceptionHandler) {
          val profileUrl = viewModel.getProfilePicUrl(user.uid!!)

          withContext(Dispatchers.Main) {
            Glide.with(binding.root).load(profileUrl).circleCrop().into(binding.profilePic)
          }
        }
      }
    }

    binding.profilePic.setOnClickListener {
      val intent = Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT)
      startActivityForResult(intent, 100)
    }

    binding.updateButton.setOnClickListener {
      val email = binding.emailEditText.text.toString()
      val bio = binding.bioEditText.text.toString()

      if (email.isEmpty()) {
        Toast.makeText(requireContext(), "Email is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (bio.isEmpty()) {
        Toast.makeText(requireContext(), "Bio is required", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      if (imageUri != null) {
        viewModel.uploadProfile(imageUri!!).addOnSuccessListener {
          viewModel.saveUser(Firebase.auth.currentUser!!.uid, email, bio).addOnSuccessListener {
            Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
          }
        }
      } else {
        viewModel.saveUser(Firebase.auth.currentUser!!.uid, email, bio).addOnSuccessListener {
          Toast.makeText(requireContext(), "Successfully updated", Toast.LENGTH_SHORT).show()
        }
      }
    }

    return binding.root
  }

  @Deprecated("Deprecated in Java")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
      imageUri = data?.data
      Glide.with(binding.root).load(imageUri).circleCrop().into(binding.profilePic)
    }
  }
}