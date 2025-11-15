package com.voidxog.wallman2.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.toSize
import androidx.core.view.WindowCompat
import com.colorata.animateaslifestyle.AnimationsPerformance
import com.colorata.animateaslifestyle.LocalAnimationsPerformance
import com.voidxog.wallman2.core.ui.util.ProvideBitmapAssetStorePreview
import com.voidxog.wallman2.core.ui.util.ProvideWindowSize
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlin.math.ln

internal val DarkColorScheme = darkColorScheme(
    primary = Purple80, secondary = PurpleGrey80, tertiary = Pink80
)

internal val LightColorScheme = lightColorScheme(
    primary = Purple40, secondary = PurpleGrey40, tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun WallManTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    val surfaceColor = colorScheme.surface
    val surfaceTintColor = colorScheme.surfaceTint
    val controller = rememberSystemUiController()
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.apply {
                WindowCompat.getInsetsController(this, view).isAppearanceLightStatusBars =
                    !darkTheme
                WindowCompat.getInsetsController(this, view).isAppearanceLightNavigationBars =
                    !darkTheme
                if (Build.VERSION.SDK_INT < 30) {
                    statusBarColor = surfaceColor.toArgb()
                    val alpha = ((4.5f * ln(3f + 1)) + 2f) / 100f
                    controller.setNavigationBarColor(
                        surfaceTintColor.copy(alpha = alpha).compositeOver(surfaceColor),
                        darkIcons = !darkTheme
                    )
                } else {
                    controller.setSystemBarsColor(Color.Transparent, darkIcons = !darkTheme)
                    setDecorFitsSystemWindows(false)
                }
            }
        }
    }
    CompositionLocalProvider(
        LocalSpacing provides remember { Spacing() },
    ) {
        ProvideWindowSize {
            MaterialTheme(
                colorScheme = colorScheme, typography = Typography
            ) {
                Surface {
                    content()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun WallManPreviewTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorScheme: ColorScheme? = null,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val finalColorScheme =
        when {
            colorScheme != null -> colorScheme
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }

    var windowSize by remember { mutableStateOf(WindowSizeClass.calculateFromSize(DpSize.Zero)) }
    val density = LocalDensity.current
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalAnimationsPerformance provides AnimationsPerformance.Full
    ) {
        Box(Modifier.onSizeChanged {
            with(density) { windowSize = WindowSizeClass.calculateFromSize(it.toSize().toDpSize())}
        }) {
            ProvideBitmapAssetStorePreview {
                ProvideWindowSize(windowSize) {
                    MaterialTheme(
                        colorScheme = finalColorScheme, typography = Typography
                    ) {
                        Surface {
                            content()
                        }
                    }
                }
            }
        }
    }
}
