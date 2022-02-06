package com.example.developerslife.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.developerslife.R
import com.example.developerslife.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private val viewModel by inject<MainViewModel>()

    private lateinit var binding: ActivityMainBinding

    private val data = mutableListOf<String>()

    private val previousButton get() = binding.previousButton
    private val nextButton get() = binding.nextButton
    private val viewPager get() = binding.viewPager
    private lateinit var adapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        previousButton.alpha = 0.2f

        viewPager.adapter = PostsAdapter().also { adapter = it }
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
//        viewPager!!.setPageTransformer(ZoomOutPageTransformer())

//        Glide.with(this).load("https://static.devli.ru/public/images/gifs/201312/7889611f-7ac4-4630-b0ba-77531eb7961c.gif").into(imageView);

        previousButton.setOnClickListener { view ->
            setBounceAnimation(view)
            showPreviousPost()
        }

        nextButton.setOnClickListener { view ->
            setBounceAnimation(view)
            showNextPost()
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = "Tab $position"
        }.attach()

        initViewModel()
    }

    private fun initViewModel() {
        viewModel.state.onEach { state ->
            Timber.d("New state = $state")
            if (state.data.isEmpty() && state.latestPostState is LatestPostState.NotLoading) {
                viewModel.loadLatestPost()
            } else if (state.data.isNotEmpty()) {
                adapter.setItems(state.data)
            }
        }.launchIn(lifecycleScope)
/*
        viewModel.state.onEach {
            when (it) {
                is DevLifeState.Loading -> {
                    Timber.d("$it")
                }
                is DevLifeState.Loaded -> {
                    Timber.d("$it")
                    if (it.data.isEmpty()) {
                        viewModel.loadLatestPost()
                    }
                }
                is DevLifeState.Error -> {
                    Timber.d("$it")
                }
            }

        }.launchIn(lifecycleScope)
*/
    }

    private fun showPreviousPost() {

    }

    private fun showNextPost() {
        viewModel.loadLatestPost()
    }

    private fun setBounceAnimation(view: View) {
        val animButton = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val interpolator = BounceInterpolator(0.1, 20)
        animButton.interpolator = interpolator
        view.startAnimation(animButton)
    }
}