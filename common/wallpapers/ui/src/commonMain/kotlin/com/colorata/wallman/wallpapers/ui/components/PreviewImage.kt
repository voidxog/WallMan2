package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.modifiers.detectRotation
import com.colorata.wallman.core.ui.modifiers.displayRotation
import com.colorata.wallman.core.ui.modifiers.drawWithMask
import com.colorata.wallman.core.ui.modifiers.rememberRotationState
import com.colorata.wallman.core.ui.shapes.ScallopShape
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.core.ui.theme.spacing
import kotlin.random.Random
import kotlin.random.nextInt

private fun getRandomShape(): Shape =
    when (Random.nextBoolean()) {
        true -> ScallopShape(density = 100f, degreeMultiplier = Random.nextInt(2..10).toFloat())
        false -> RoundedCornerShape(Random.nextInt(10..50))
    }

@Composable
internal fun PreviewImage(
    resource: String,
    downloadProgress: () -> Float,
    modifier: Modifier = Modifier
) {
    val shape = remember {
        getRandomShape()
    }
    val rotationState = rememberRotationState()
    val animation = MaterialTheme.animation

    Box(
        modifier
            .detectRotation(rotationState)
            .statusBarsPadding(), contentAlignment = Alignment.Center
    ) {
        Arc(progress = { downloadProgress() }, width = {
            if (rotationState.isRotationInProgress) 8f
            else 0f
        }, Modifier
            .displayRotation(rotationState)
            .fillMaxSize(0.9f)
            .aspectRatio(1f), shape
        )
        AnimatedContent(
            targetState = resource, transitionSpec = {
                fadeIn(animation.emphasized()) togetherWith fadeOut(animation.emphasized())
            }, label = "", modifier = Modifier
                .fillMaxSize(0.9f)
                .aspectRatio(1f)
        ) { imageName ->
            Image(
                bitmap = bitmapAsset(imageName), contentDescription = "", modifier = Modifier
                    .displayRotation(rotationState, layer = 0.5f)
                    .clip(shape)
                    .fillMaxSize()
                    .aspectRatio(1f), contentScale = ContentScale.Crop
            )
        }
        Arc(progress = { downloadProgress() }, width = { progress ->
            if (rotationState.isRotationInProgress) 0f
            else if (progress == 100f) 1f
            else 3f
        }, Modifier
            .displayRotation(rotationState, layer = 1f)
            .fillMaxSize(0.9f)
            .aspectRatio(1f), shape
        )
    }
}

@LightDarkPreview
@Composable
private fun PreviewImagePreview() {
    WallManPreviewTheme {
        PreviewImage("", downloadProgress = { 100f })
    }
}

@Composable
private fun Arc(
    progress: () -> Float,
    width: (progress: Float) -> Float,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape
) {
    val animatedProgress by animateFloatAsState(progress(), label = "")
    val start = if (animatedProgress != 100f) rememberInfiniteTransition(label = "").animateFloat(
        initialValue = -90f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ), repeatMode = RepeatMode.Restart
        ),
        label = ""
    ).value else -90f
    val animatedWidth =
        MaterialTheme.spacing.extraSmall * animateFloatAsState(targetValue = remember {
            derivedStateOf {
                width(animatedProgress)
            }
        }.value, label = "").value
    val borderColor = MaterialTheme.colorScheme.primary
    val layoutDirection = LocalLayoutDirection.current
    val density = LocalDensity.current
    val border = BorderStroke(animatedWidth, borderColor)
    Box(modifier = modifier.clip(shape)) {
        Canvas(
            modifier = Modifier
                .drawWithMask {
                    if (animatedWidth.value != 0f) {
                        drawArc(
                            border.brush,
                            start + 3.6f * animatedProgress,
                            360f - 3.6f * animatedProgress,
                            useCenter = true,
                            blendMode = BlendMode.DstOut,
                            topLeft = -center,
                            size = size * 2F
                        )
                    }
                }
                .fillMaxSize()
        ) {
            if (animatedWidth.value != 0f) {
                drawOutline(
                    shape.createOutline(size, layoutDirection, density),
                    brush = border.brush,
                    style = Stroke(border.width.toPx(), cap = StrokeCap.Round)
                )
            }
        }
    }
}