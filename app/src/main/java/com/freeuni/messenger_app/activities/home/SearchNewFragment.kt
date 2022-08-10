package com.freeuni.messenger_app.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.freeuni.messenger_app.databinding.SearchProfileBinding
import com.freeuni.messenger_app.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchNewFragment : Fragment() {
  private lateinit var binding: SearchProfileBinding
  private val viewModel: HomeViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = SearchProfileBinding.inflate(inflater, container, false)

    val searchedAdapter = SearchAdapter(requireContext(), viewModel.userRepository, ArrayList())

    binding.searchedProfiles.adapter = searchedAdapter
    val linearLayoutManager = LinearLayoutManager(requireContext())
    linearLayoutManager.stackFromEnd = true
    binding.searchedProfiles.layoutManager = linearLayoutManager;

    binding.searchBar.doOnTextChanged { text, start, before, count ->
      if (text.isNullOrEmpty()) {
        searchedAdapter.searchedList = ArrayList()
        searchedAdapter.notifyDataSetChanged()
        return@doOnTextChanged
      }

      CoroutineScope(Dispatchers.IO).launch {
        searchedAdapter.searchedList = viewModel.searchUsers(text.toString())

        CoroutineScope(Dispatchers.Main).launch {
          searchedAdapter.notifyDataSetChanged()
        }
      }
    }

    return binding.root
  }
}