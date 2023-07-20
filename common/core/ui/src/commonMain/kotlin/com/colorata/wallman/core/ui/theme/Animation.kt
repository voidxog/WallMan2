package com.colorata.wallman.core.ui.theme

import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.slideHorizontally
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.wallman.core.data.Animation

fun Animation.emphasizedVerticalSlide(from: Float = 100f) = fade(animationSpec = emphasized()) +
        slideVertically(
            from, animationSpec = emphasized()
        )

fun Animation.emphasizedHorizontalSlide(from: Float = -100f) = fade(animationSpec = emphasized()) +
        slideHorizontally(
            from, animationSpec = emphasized()
        )

fun Animation.emphasizedFade() = fade(animationSpec = emphasized())