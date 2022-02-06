package com.example.developerslife.presentation

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.developerslife.R
import com.example.developerslife.databinding.ActivityMainBinding
import com.example.developerslife.presentation.feeds.FeedsFragment
import com.example.developerslife.presentation.random.RandomFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnFeeds.setOnClickListener { showFeeds() }
        binding.btnRandom.setOnClickListener { showRandom() }

        if (savedInstanceState == null) {
            showFeeds()
        }
    }

    private fun showRandom() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, RandomFragment())
            .commit()
    }

    private fun showFeeds() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FeedsFragment())
            .commit()
    }
}