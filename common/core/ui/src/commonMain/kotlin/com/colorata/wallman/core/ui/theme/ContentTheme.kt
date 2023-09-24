package com.colorata.wallman.core.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import com.colorata.animateaslifestyle.AnimationsPerformance
import com.colorata.animateaslifestyle.LocalAnimationsPerformance
import com.colorata.wallman.core.data.bitmapAsset
import com.colorata.wallman.core.data.memoizeHash
import com.colorata.wallman.core.ui.util.ProvideWindowSize
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
 * Uses Material Color Utilities api to find dominant color.
 * @param chroma chroma of generated color
 */
private fun ImageBitmap.overallDominantColor(chroma: Int = 30): Int {
    val buffer = IntArray(width * height + 1)
    readPixels(buffer)
    asAndroidBitmap().getPixels(buffer, 0, width, 0, 0, width - 1, height - 1)
    return QuantizerCelebi.quantize(buffer, maxColors = 1).toList()[0].first.let {
        Hct
            .fromInt(it)
            .apply { setChroma(chroma.toDouble()) }
            .toInt()
    }
}

fun ImageBitmap.extractColorScheme(darkTheme: Boolean): ColorScheme {
    val dominant = overallDominantColor()
    val scheme = if (darkTheme) Scheme.darkContent(dominant) else Scheme.lightContent(dominant)
    return scheme.toColorScheme()
}

private val extractedColors =
    memoizeHash({ assetName: String, (_, darkTheme) -> assetName to darkTheme }) { _: String, pair: Pair<ImageBitmap, Boolean> ->
        val (bitmap, darkTheme) = pair
        bitmap.extractColorScheme(darkTheme)
    }

@Composable
fun WallManContentTheme(
    assetName: String,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val image = bitmapAsset(assetName)
    var colorScheme by remember { mutableStateOf<ColorScheme?>(null) }
    LaunchedEffect(assetName, darkTheme) {
        colorScheme = extractedColors(assetName, image to darkTheme)
    }

    val scheme = colorScheme
    val finalColorScheme =
        when {
            scheme != null -> scheme
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalAnimationsPerformance provides AnimationsPerformance.Full
    ) {
        ProvideWindowSize {
            MaterialTheme(
                colorScheme = finalColorScheme, typography = Typography
            ) {
                content()
            }
        }
    }
}