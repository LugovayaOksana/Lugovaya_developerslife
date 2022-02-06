package com.example.developerslife.util

import android.view.View
import android.view.animation.AnimationUtils
import com.example.developerslife.R
import com.example.developerslife.presentation.BounceInterpolator

fun View.startBounceAnimation() {
    val animButton = AnimationUtils.loadAnimation(context, R.anim.bounce)
    val interpolator = BounceInterpolator(0.1, 20)
    animButton.interpolator = interpolator
    startAnimation(animButton)
}