package com.example.developerslife.presentation.random

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.developerslife.databinding.FragmentRandomBinding
import com.example.developerslife.util.startBounceAnimation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class RandomFragment: Fragment() {

    private var _binding: FragmentRandomBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RandomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRandomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
//        viewPager!!.setPageTransformer(ZoomOutPageTransformer())
    }

    private fun initViewModel() {
        viewModel.state.onEach {
            binding.progress.isVisible = false
            binding.image.isVisible = false
            binding.description.isVisible = false
            binding.errorMessage.isVisible = false
            binding.previousButton.isEnabled = it.isBackEnabled
            binding.nextButton.isEnabled = it.isForwardEnabled
            when(it) {
                is RandomState.Error -> {
                    binding.errorMessage.isVisible = true
                    binding.errorMessage.text = it.message
                }
                is RandomState.Loaded -> {
                    binding.image.isVisible = true
                    binding.description.isVisible = true
                    Glide.with(binding.image).load(it.post.gifURL).into(binding.image)
                    binding.description.text = it.post.description
                }
                RandomState.Loading -> {
                    binding.progress.isVisible = true
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initViews() {
        with(binding) {
            previousButton.alpha = 0.2f
            //viewPager.adapter = PostsAdapter().also { adapter = it }
            //viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            previousButton.setOnClickListener { view ->
                view.startBounceAnimation()
                viewModel.loadPreviousPost()
            }

            nextButton.setOnClickListener { view ->
                view.startBounceAnimation()
                viewModel.loadNextPost()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}