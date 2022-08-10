package com.freeuni.messenger_app.activities.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.freeuni.messenger_app.R
import com.freeuni.messenger_app.activities.chat.ChatActivity
import com.freeuni.messenger_app.databinding.ProfileHeadBinding
import com.freeuni.messenger_app.models.User
import com.freeuni.messenger_app.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchAdapter(
  val context: Context,
  val userRepository: UserRepository,
  var searchedList: List<User>
) :
  RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ProfileHeadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val user = searchedList[position]

    CoroutineScope(Dispatchers.IO).launch {
      val profilePicUrl = userRepository.getProfilePicUrl(user.uid!!);

      CoroutineScope(Dispatchers.Main).launch {
        if (profilePicUrl != null) {
          Glide.with(holder.itemView.context).load(profilePicUrl).circleCrop()
            .into(holder.binding.profilePic)
        } else {
          Glide.with(holder.itemView.context).load(R.drawable.avatar_image_placeholder).circleCrop()
            .into(holder.binding.profilePic)
        }
        holder.binding.personName.setText(user.email)
        holder.binding.profession.setText(user.bio)

        holder.binding.root.setOnClickListener {
          val intent =
            Intent(context, ChatActivity::class.java)
              .putExtra("userEmail", user.email)
              .putExtra("userId", user.uid)
              .putExtra("userBio", user.bio)
              .putExtra("userProfile", if (profilePicUrl == null) "" else profilePicUrl.toString())

          context.startActivity(intent)
        }
      }
    }

  }

  override fun getItemCount(): Int {
    return searchedList.size
  }

  class ViewHolder(val binding: ProfileHeadBinding) : RecyclerView.ViewHolder(binding.root)
}