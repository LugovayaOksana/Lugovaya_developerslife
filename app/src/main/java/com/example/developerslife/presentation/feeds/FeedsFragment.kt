package com.example.developerslife.presentation.feeds

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.developerslife.databinding.FragmentFeedsBinding
import com.example.developerslife.presentation.components.FeedsScreen
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedsFragment: Fragment() {

    private var _binding: FragmentFeedsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FeedsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedsBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    FeedsScreen(viewModel)
                }
            }
        }
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