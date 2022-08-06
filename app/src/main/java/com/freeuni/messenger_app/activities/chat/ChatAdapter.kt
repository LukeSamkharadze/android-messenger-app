package com.freeuni.messenger_app.activities.chat

import android.content.Context
import android.provider.Telephony
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.freeuni.messenger_app.activities.home.FriendsAdapter
import com.freeuni.messenger_app.databinding.ChatHeadBinding
import com.freeuni.messenger_app.databinding.ItemChatMeBinding
import com.freeuni.messenger_app.databinding.ItemChatOtherBinding
import com.freeuni.messenger_app.extensions.toMessageDate
import com.freeuni.messenger_app.models.Friend
import com.freeuni.messenger_app.models.Message
import java.text.SimpleDateFormat


class ChatAdapter(val signedInUserId: String, val context: Context, var messages: List<Message>) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  val RECEIVE = 1
  val SENT = 2

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    if (viewType == SENT) {
      val binding = ItemChatMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      return ChatAdapter.SentViewHolder(binding)
    }
    val binding = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ChatAdapter.ReceivedViewHolder(binding)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val currMessage = messages[position];

    if (holder.javaClass == SentViewHolder::class.java) {
      val viewHolder = holder as SentViewHolder
      viewHolder.binding.textGchatMessageMe.setText(currMessage.message);
      viewHolder.binding.textGchatTimestampMe.setText(
        SimpleDateFormat("HH:mm").format(currMessage.date!!.toDate()).uppercase()
      )
    } else if (holder.javaClass == ReceivedViewHolder::class.java) {
      val viewHolder = holder as ReceivedViewHolder
      viewHolder.binding.textGchatMessageOther.setText(currMessage.message);
      viewHolder.binding.textGchatTimestampOther.setText(
        SimpleDateFormat("HH:mm").format(currMessage.date!!.toDate()).uppercase()
      )
    }
  }

  override fun getItemCount(): Int {
    return messages.size
  }

  override fun getItemViewType(position: Int): Int {

    val curMessage = messages[position];

    if (signedInUserId == curMessage.from) {
      return SENT
    }

    return RECEIVE
  }

  class SentViewHolder(val binding: ItemChatMeBinding) : RecyclerView.ViewHolder(binding.root)
  class ReceivedViewHolder(val binding: ItemChatOtherBinding) :
    RecyclerView.ViewHolder(binding.root)
}