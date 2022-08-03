package com.freeuni.messenger_app.activities.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.freeuni.messenger_app.databinding.ChatBinding

class ChatActivity : AppCompatActivity() {
  private lateinit var binding: ChatBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ChatBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val email = intent.getStringExtra("userEmail")
    val uid = intent.getStringExtra("userId")
    val bio = intent.getStringExtra("userBio")
    val profileUrl = intent.getStringExtra("userProfile")

    binding.personName.setText(email)
    if (profileUrl != null) {
      Glide.with(binding.root).load(profileUrl).circleCrop().into(binding.imageView2)
    }

    binding.button2.setOnClickListener {
      finish()
    }
  }
}