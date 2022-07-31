package com.freeuni.messenger_app.activities.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.freeuni.messenger_app.databinding.ProfilePageBinding

class ProfileFragment : Fragment() {
  private lateinit var binding: ProfilePageBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = ProfilePageBinding.inflate(inflater, container, false)
    return binding.root
  }
}