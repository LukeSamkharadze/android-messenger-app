package com.freeuni.messenger_app.activities.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.freeuni.messenger_app.activities.home.FriendsAdapter
import com.freeuni.messenger_app.databinding.ChatBinding
import com.freeuni.messenger_app.models.Friend
import com.freeuni.messenger_app.models.FriendDocument
import com.freeuni.messenger_app.models.Message
import com.freeuni.messenger_app.models.MessagesListDocument
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.model.DocumentKey
import com.google.firebase.firestore.model.ResourcePath

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

    val chatAdapter =
      ChatAdapter(FirebaseAuth.getInstance().currentUser!!.uid, this, emptyList())

    binding.recyclerGchat.adapter = chatAdapter
    val linearLayoutManager = LinearLayoutManager(this)
    linearLayoutManager.stackFromEnd = true
    binding.recyclerGchat.layoutManager = linearLayoutManager;

    binding.bottomAppBar.setElevation(0F)

    binding.personName.setText(receiverEmail)
    if (receiverProfileUrl != "") {
      Glide.with(binding.root).load(receiverProfileUrl).circleCrop().into(binding.imageView2)
    }

    binding.button2.setOnClickListener {
      finish()
    }

    val senderId = FirebaseAuth.getInstance().currentUser!!.uid
    var messagesId: String?;

    if (senderId.compareTo(receiverId) < 0) {
      messagesId = "${senderId}-${receiverId}"
    } else {
      messagesId = "${receiverId}-${senderId}"
    }

    FirebaseFirestore.getInstance().collection("messages").document(messagesId)
      .set(emptyMap<String, FriendDocument>(), SetOptions.merge()).addOnSuccessListener {
        FirebaseFirestore.getInstance().collection("messages").document(messagesId)
          .addSnapshotListener { value, error ->
            if (error == null && value != null) {
              val messages = value.toObject(MessagesListDocument::class.java);
              if (messages?.messages != null) {
                chatAdapter.messages = messages.messages!!
                chatAdapter.notifyDataSetChanged()
                binding.recyclerGchat.scrollToPosition(messages.messages!!.size - 1)
              }
            }
          }
      }

    binding.buttonGchatSend.setOnClickListener {
      val message = binding.editGchatMessage.text

      if (message.isEmpty()) {
        return@setOnClickListener
      }

      val newMessage = Message()
      newMessage.message = message.toString();
      newMessage.date = Timestamp.now()
      newMessage.from = FirebaseAuth.getInstance().currentUser!!.uid;

      binding.editGchatMessage.setText("")


      val docData = hashMapOf(
        receiverId to FriendDocument(
          FirebaseFirestore.getInstance().document("/users/${receiverId}"),
          message.toString(),
          Timestamp.now(),
        )
      )

      FirebaseFirestore.getInstance().collection("friends")
        .document(FirebaseAuth.getInstance().currentUser!!.uid)
        .set(
          hashMapOf(
            "friends" to docData
          ),
          SetOptions.merge()
        )

      FirebaseFirestore.getInstance().collection("messages").document(messagesId)
        .update("messages", FieldValue.arrayUnion(newMessage))
    }
  }
}