package com.freeuni.messenger_app.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.freeuni.messenger_app.databinding.FragmentChatsBinding
import com.freeuni.messenger_app.databinding.FragmentSignInBinding
import com.freeuni.messenger_app.models.Friend
import com.freeuni.messenger_app.viewmodels.HomeViewModel

class ChatsFragment : Fragment() {
  private lateinit var binding: FragmentChatsBinding
  private val viewModel: HomeViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentChatsBinding.inflate(inflater, container, false)

    val friendsList = ArrayList<Friend>()
    val friendsAdapter = FriendsAdapter(friendsList)

    binding.chatHeads.adapter = friendsAdapter
    binding.chatHeads.layoutManager = LinearLayoutManager(requireContext())

    viewModel.friendsLiveData.observe(requireActivity()) {
      friendsAdapter.friendsList = it
      friendsAdapter.notifyDataSetChanged()
    }

    return binding.root
  }
}