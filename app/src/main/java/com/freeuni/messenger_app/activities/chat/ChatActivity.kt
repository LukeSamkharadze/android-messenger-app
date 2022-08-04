package com.freeuni.messenger_app.activities.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.freeuni.messenger_app.databinding.ChatBinding
import com.freeuni.messenger_app.models.MessagesListDocument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChatActivity : AppCompatActivity() {
  private lateinit var binding: ChatBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ChatBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val receiverEmail = intent.getStringExtra("userEmail")!!
    val receiverId = intent.getStringExtra("userId")!!
    val receiverBio = intent.getStringExtra("userBio")!!
    val receiverProfileUrl = intent.getStringExtra("userProfile")!!

    binding.personName.setText(receiverEmail)
    if (receiverProfileUrl != "") {
      Glide.with(binding.root).load(receiverProfileUrl).circleCrop().into(binding.imageView2)
    }

    binding.button2.setOnClickListener {
      finish()
    }

    val senderId = FirebaseAuth.getInstance().currentUser!!.uid
    var messagesId: String?;

    if(senderId.compareTo(receiverId) < 0 ) {
      messagesId = "${senderId}-${receiverId}"
    } else {
      messagesId = "${receiverId}-${senderId}"
    }

    FirebaseFirestore.getInstance().collection("messages").document(messagesId).addSnapshotListener { value, error ->
      var messages = value!!.toObject(MessagesListDocument::class.java);

    }
  }
}