package com.voidxog.wallman2.wallpapers.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.voidxog.wallman2.core.data.Polyglot
import com.voidxog.wallman2.core.data.animation
import com.voidxog.wallman2.core.data.rememberString
import com.voidxog.wallman2.core.data.simplifiedLocaleOf
import com.voidxog.wallman2.core.ui.DarkUIMode
import com.voidxog.wallman2.core.ui.LightUIMode
import com.voidxog.wallman2.core.ui.components.FontVariables
import com.voidxog.wallman2.core.ui.components.Text
import com.voidxog.wallman2.core.ui.modifiers.disabledWhen
import com.voidxog.wallman2.core.ui.theme.WallManPreviewTheme
import com.voidxog.wallman2.core.ui.theme.spacing

@Composable
fun BigChip(
    onClick: () -> Unit,
    selected: Boolean,
    text: Polyglot,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val background by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.tertiary
        else MaterialTheme.colorScheme.surfaceVariant,
        label = "Background"
    )
    val roundness by animateIntAsState(
        targetValue = if (selected) 20 else 50, label = "Roundness",
        animationSpec = MaterialTheme.animation.emphasizedDecelerate()
    )
    val weight by animateFloatAsState(
        targetValue = if (selected) 900f else 400f, label = "Weight",
        animationSpec = MaterialTheme.animation.emphasizedDecelerate()
    )
    val color by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSurfaceVariant,
        label = "Color",
        animationSpec = MaterialTheme.animation.emphasizedDecelerate()
    )
    Text(
        text = rememberString(string = text),
        modifier = modifier
            .graphicsLayer {
                shape = RoundedCornerShape(roundness)
                clip = true
            }
            .disabledWhen(!enabled)
            .drawBehind {
                drawRect(background)
            }
            .clickable {
                onClick()
            }
            .padding(vertical = MaterialTheme.spacing.large)
            .wrapContentHeight(align = Alignment.CenterVertically),
        fontVariables = FontVariables(
            weight = weight
        ),
        color = color,
        textAlign = TextAlign.Center
    )
}

private class SelectProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(false, true)
}

@Preview(
    uiMode = LightUIMode
)
@Preview(
    uiMode = DarkUIMode
)
@Composable
private fun BigChipPreview(@PreviewParameter(SelectProvider::class) selected: Boolean) {
    WallManPreviewTheme {
        BigChip(
            onClick = { },
            selected = selected,
            text = remember { simplifiedLocaleOf("Select") },
            Modifier.width(200.dp)
        )
    }
}
