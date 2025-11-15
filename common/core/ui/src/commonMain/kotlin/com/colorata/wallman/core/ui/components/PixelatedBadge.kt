package com.voidxog.wallman2.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.voidxog.wallman2.core.ui.LightDarkPreview
import com.voidxog.wallman2.core.ui.R
import com.voidxog.wallman2.core.ui.shapes.LeafShape
import com.voidxog.wallman2.core.ui.theme.WallManPreviewTheme
import com.voidxog.wallman2.core.ui.theme.spacing

@Composable
fun PixelatedBadge(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer
) {
    val spacing = MaterialTheme.spacing
    androidx.compose.material3.Text(
        text,
        fontFamily = remember { FontFamily(Font(R.font.sf_pixelate)) },
        modifier = modifier
            .background(
                color = containerColor,
                shape = remember {
                    LeafShape(spacing)
                }
            )
            .padding(MaterialTheme.spacing.medium)
            .wrapContentHeight(Alignment.CenterVertically),
        style = MaterialTheme.typography.labelMedium,
        color = contentColorFor(containerColor)
    )
}

@LightDarkPreview
@Composable
private fun PixelatedBadgePreview() {
    WallManPreviewTheme {
        PixelatedBadge("RETRO")
    }
}
