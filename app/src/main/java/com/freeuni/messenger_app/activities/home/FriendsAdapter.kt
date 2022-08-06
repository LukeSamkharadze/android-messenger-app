package com.freeuni.messenger_app.activities.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.activities.chat.ChatActivity
import com.freeuni.messenger_app.databinding.ChatHeadBinding
import com.freeuni.messenger_app.extensions.toMessageDate
import com.freeuni.messenger_app.models.Friend

class FriendsAdapter(val context: Context, var friendsList: List<Friend>) :
  RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ChatHeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val friend = friendsList[position]
    if (friend.profilePicUrl != null) {
      Glide.with(holder.itemView.context).load(friend.profilePicUrl).circleCrop()
        .into(holder.binding.profilePic)
    } else {
      Glide.with(holder.itemView.context).load(R.drawable.avatar_image_placeholder).circleCrop()
        .into(holder.binding.profilePic)
    }
    holder.binding.personName.setText(friend.user.email)
    holder.binding.lastMessage.setText(friend.lastMessage)
    holder.binding.msgtime.setText(friend.lastMessageDate.toMessageDate())

    holder.binding.root.setOnClickListener {
      val intent =
        Intent(context, ChatActivity::class.java)
          .putExtra("userEmail", friend.user.email)
          .putExtra("userId", friend.user.uid)
          .putExtra("userBio", friend.user.bio)
          .putExtra("userProfile", if (friend.profilePicUrl == null) "" else friend.profilePicUrl.toString())

      context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int {
    return friendsList.size
  }

  class ViewHolder(val binding: ChatHeadBinding) : RecyclerView.ViewHolder(binding.root)
}