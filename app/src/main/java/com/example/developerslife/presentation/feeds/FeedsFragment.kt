package com.example.developerslife.presentation.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.developerslife.databinding.FragmentFeedsBinding
import com.google.android.material.tabs.TabLayoutMediator

class FeedsFragment: Fragment() {

    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       /* TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = "Tab $position"
        }.attach()*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}