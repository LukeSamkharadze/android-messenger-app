package com.freeuni.messenger_app.activities.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.databinding.ChatHeadBinding
import com.freeuni.messenger_app.extensions.toMessageDate
import com.freeuni.messenger_app.models.Friend

class FriendsAdapter(var friendsList: List<Friend>) :
  RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ChatHeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (friendsList[position].profilePicUrl != null) {
      Glide.with(holder.itemView.context).load(friendsList[position].profilePicUrl).circleCrop()
        .into(holder.binding.profilePic)
    } else {
      Glide.with(holder.itemView.context).load(R.drawable.avatar_image_placeholder).circleCrop()
        .into(holder.binding.profilePic)
    }
    holder.binding.personName.setText(friendsList[position].user.email)
    holder.binding.lastMessage.setText(friendsList[position].lastMessage)
    holder.binding.msgtime.setText(friendsList[position].lastMessageDate.toMessageDate())
  }

  override fun getItemCount(): Int {
    return friendsList.size
  }

  class ViewHolder(val binding: ChatHeadBinding) : RecyclerView.ViewHolder(binding.root)
}