package com.colorata.wallman.core.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.materialFadeThrough
import com.colorata.wallman.core.data.materialSharedAxisX

@Composable
fun ScreenBackground(
    bitmap: ImageBitmap,
    modifier: Modifier = Modifier,
    imageFraction: Float = 0.5f
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val animation = MaterialTheme.animation
    AnimatedContent(bitmap, transitionSpec = {
        fadeIn(animation.emphasized()) togetherWith fadeOut(animation.emphasized())
    }, label = "") { image ->
        Box(
            modifier
                .fillMaxSize()
                .blur(5.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(image, contentDescription = null,
                Modifier
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, surfaceColor), endY = size.height
                            )
                        )

                    }
                    .fillMaxWidth()
                    .fillMaxHeight(imageFraction),
                contentScale = ContentScale.Crop)
        }
    }
}