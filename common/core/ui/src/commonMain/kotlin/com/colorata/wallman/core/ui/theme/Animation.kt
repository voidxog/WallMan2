package com.voidxog.wallman2.core.ui.theme

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.scale
import com.colorata.animateaslifestyle.scaleHorizontally
import com.colorata.animateaslifestyle.scaleVertically
import com.colorata.animateaslifestyle.slide
import com.colorata.animateaslifestyle.slideHorizontally
import com.colorata.animateaslifestyle.slideVertically
import com.voidxog.wallman2.core.data.Animation
import com.voidxog.wallman2.core.data.AnimationType

@Composable
fun Animation.emphasizedEnterExit(
    from: Float = 100f,
    scaleAlignment: Alignment.Vertical = Alignment.Top
) = remember(animationType) {
    when (animationType) {
        AnimationType.Slide -> emphasizedSlide(from, Orientation.Vertical)
        AnimationType.Scale -> emphasizedVerticalScale(
            TransformOrigin(
                0.5f,
                getPivotYFrom(scaleAlignment)
            )
        )

        AnimationType.Fade -> emphasizedFade()
    }
}

private fun getPivotYFrom(alignment: Alignment.Vertical) = when (alignment) {
    Alignment.Top -> 0f
    Alignment.CenterVertically -> 0.5f
    Alignment.Bottom -> 1f
    else -> 0f
}

private fun Animation.emphasizedSlide(from: Float, orientation: Orientation) =
    fade(animationSpec = emphasized()) +
            slide(
                Offset(
                    x = if (orientation == Orientation.Horizontal) from else 0f,
                    y = if (orientation == Orientation.Vertical) 100f else 0f
                ), animationSpec = emphasized()
            ) + scale(from = 0.98f, animationSpec = emphasized())

private fun Animation.emphasizedVerticalScale(transformOrigin: TransformOrigin) =
    scaleVertically(
        from = 0.8f,
        animationSpec = emphasized(),
        transformOrigin
    ) + fade(animationSpec = emphasized()) + slideVertically(
        (transformOrigin.pivotFractionY - 1f) * 100f,
        emphasized()
    )

private fun Animation.emphasizedHorizontalScale(transformOrigin: TransformOrigin) =
    scaleHorizontally(
        from = 0.8f,
        animationSpec = emphasized(),
        transformOrigin
    ) + fade(animationSpec = emphasized()) + slideHorizontally(
        (transformOrigin.pivotFractionX - 1f) * 100f,
        emphasized()
    )


@Composable
fun Animation.emphasizedHorizontalEnterExit(from: Float = -100f) = remember(animationType) {
    when (animationType) {
        AnimationType.Slide -> emphasizedSlide(from, Orientation.Horizontal)
        AnimationType.Scale -> emphasizedHorizontalScale(TransformOrigin(0f, 0.5f))
        AnimationType.Fade -> emphasizedFade()
    }
}

fun Animation.emphasizedFade() = fade(animationSpec = emphasized())
