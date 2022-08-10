package com.freeuni.messenger_app.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.freeuni.messenger_app.databinding.MainPageBinding
import com.freeuni.messenger_app.databinding.SearchProfileBinding
import com.freeuni.messenger_app.viewmodels.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchNewActivity : AppCompatActivity() {
  private lateinit var binding: SearchProfileBinding
  private lateinit var viewModel: HomeViewModel


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = SearchProfileBinding.inflate(layoutInflater)
    setContentView(binding.root)

    viewModel =
      ViewModelProvider(this)[HomeViewModel::
      class.java]

    val searchedAdapter = SearchAdapter(this, viewModel.userRepository, ArrayList())

    binding.searchedProfiles.adapter = searchedAdapter
    val linearLayoutManager = LinearLayoutManager(this)
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
  }
}