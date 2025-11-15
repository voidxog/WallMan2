package com.voidxog.wallman2.wallpapers.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.voidxog.wallman2.core.data.Polyglot
import com.voidxog.wallman2.core.data.materialSharedAxisX
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.ui.LightDarkPreview
import com.voidxog.wallman2.core.ui.theme.WallManPreviewTheme
import com.voidxog.wallman2.wallpapers.firstBaseWallpaper
import com.voidxog.wallman2.wallpapers.walls

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
