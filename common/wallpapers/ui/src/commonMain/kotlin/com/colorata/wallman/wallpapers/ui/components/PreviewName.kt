package com.colorata.wallman.wallpapers.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.colorata.wallman.core.data.Polyglot
import com.colorata.wallman.core.data.materialSharedAxisX
import com.colorata.wallman.core.data.rememberString
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import com.colorata.wallman.wallpapers.firstBaseWallpaper
import com.colorata.wallman.wallpapers.walls

@Composable
internal fun PreviewName(previewName: Polyglot, modifier: Modifier = Modifier) {
    AnimatedContent(targetState = previewName, transitionSpec = {
        materialSharedAxisX(true, 100) using SizeTransform(clip = false)
    }, modifier = modifier, label = "") { name ->
        Text(
            text = rememberString(string = name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@LightDarkPreview
@Composable
private fun PreviewNamePreview() {
    WallManPreviewTheme {
        PreviewName(walls[0].firstBaseWallpaper().previewName)
    }
}