package com.colorata.wallman.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import com.colorata.wallman.core.data.memoize
import dev.sasikanth.material.color.utilities.hct.Hct
import dev.sasikanth.material.color.utilities.quantize.QuantizerCelebi
import dev.sasikanth.material.color.utilities.scheme.Scheme

fun Scheme.toColorScheme(): ColorScheme {
    return darkColorScheme(
        primary = Color(primary),
        onPrimary = Color(onPrimary),
        primaryContainer = Color(primaryContainer),
        onPrimaryContainer = Color(onPrimaryContainer),
        inversePrimary = Color(inversePrimary),
        secondary = Color(secondary),
        onSecondary = Color(onSecondary),
        secondaryContainer = Color(secondaryContainer),
        onSecondaryContainer = Color(onSecondaryContainer),
        tertiary = Color(tertiary),
        onTertiary = Color(onTertiary),
        tertiaryContainer = Color(tertiaryContainer),
        onTertiaryContainer = Color(onTertiaryContainer),
        background = Color(background),
        onBackground = Color(onBackground),
        surface = Color(surface),
        onSurface = Color(onSurface),
        surfaceVariant = Color(surfaceVariant),
        onSurfaceVariant = Color(onSurfaceVariant),
        inverseSurface = Color(inverseSurface),
        inverseOnSurface = Color(inverseOnSurface),
        error = Color(error),
        onError = Color(onError),
        errorContainer = Color(errorContainer),
        onErrorContainer = Color(onErrorContainer),
        outline = Color(outline),
        outlineVariant = Color(outlineVariant),
        scrim = Color(scrim)
    )
}

/**
 * Finds hue with maximum pixels.
 * Usually more saturated.
 */
private fun ImageBitmap.hueDominantColor(): Int {
    val buffer = IntArray(width * height + 1)
    readPixels(buffer)
    val hue = dominantHueFrom(buffer)
    return Color.hsl(hue, 1f, 0.85f).toArgb()
}

/**
 * Uses Material Color Utilities api to find dominant color.
 * Usually less saturated.
 */
private fun ImageBitmap.overallDominantColor(): Int {
    val buffer = IntArray(width * height + 1)
    readPixels(buffer)
    return QuantizerCelebi.quantize(buffer, maxColors = 10).map { it.key }.maxBy { Hct.fromInt(it).getChroma() }
}

private fun dominantHueFrom(colors: IntArray): Float {
    val hueCountMap = mutableMapOf<Float, Int>()
    colors.forEach { color ->
        val hct = Hct.fromInt(color)
        if (hct.getChroma() <= 20 || hct.getTone() !in 20f..80f) return@forEach
        val hue = hct.getHue().toFloat()
        hueCountMap[hue] = hueCountMap[hue] ?: 1
    }
    if (hueCountMap.isEmpty()) return 200f
    return hueCountMap.maxBy { it.value }.key
}

fun ImageBitmap.extractColorScheme(darkTheme: Boolean): ColorScheme {
    val dominant = overallDominantColor()
    val scheme = if (darkTheme) Scheme.darkContent(dominant) else Scheme.lightContent(dominant)
    return scheme.toColorScheme()
}

private val extractedColors =
    memoize { bitmap: ImageBitmap, darkTheme: Boolean ->
        bitmap.extractColorScheme(darkTheme)
    }

@Composable
fun WallManContentTheme(
    image: ImageBitmap,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme by produceState<ColorScheme?>(null, key1 = image) {
        value = extractedColors(image, darkTheme)
    }
    WallManPreviewTheme(darkTheme = darkTheme, colorScheme = colorScheme) {
        content()
    }
}