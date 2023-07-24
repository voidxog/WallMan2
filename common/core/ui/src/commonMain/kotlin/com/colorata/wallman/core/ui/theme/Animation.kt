package com.colorata.wallman.core.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.scale
import com.colorata.animateaslifestyle.slideHorizontally
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.wallman.core.data.Animation

@Composable
fun Animation.emphasizedVerticalSlide(from: Float = 100f) = remember {
    fade(animationSpec = emphasized()) +
            slideVertically(
                from, animationSpec = emphasized()
            ) + scale(from = 0.98f, animationSpec = emphasized())
}

@Composable
fun Animation.emphasizedHorizontalSlide(from: Float = -100f) = remember {
    fade(animationSpec = emphasized()) +
            slideHorizontally(
                from, animationSpec = emphasized()
            )
}

fun Animation.emphasizedFade() = fade(animationSpec = emphasized())