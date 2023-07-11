package com.colorata.wallman.shared

import android.os.Build
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.isCompositionLaunched
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.*
import com.colorata.wallman.categories.ui.categoriesScreen
import com.colorata.wallman.categories.ui.categoryDetailsScreen
import com.colorata.wallman.core.data.*
import com.colorata.wallman.core.di.LocalGraph
import com.colorata.wallman.core.ui.theme.LocalPaddings
import com.colorata.wallman.settings.memory.ui.cacheScreen
import com.colorata.wallman.settings.mirror.ui.mirrorScreen
import com.colorata.wallman.settings.overview.ui.aboutScreen
import com.colorata.wallman.settings.overview.ui.settingsScreen
import com.colorata.wallman.wallpapers.MainDestination
import com.colorata.wallman.wallpapers.ui.mainScreen
import com.colorata.wallman.wallpapers.ui.wallpaperDetailsScreen
import com.colorata.wallman.widget.ui.shapePickerScreen

@Composable
fun Navigation(startDestination: Destination = Destinations.MainDestination()) {
    val navController = LocalGraph.current.coreModule.navigationController
    val graph = LocalGraph.current
    Scaffold(bottomBar = {
        val selected by navController.currentPath.collectAsState()
        BottomBar(selected) { navController.resetRootTo(destination(it)) }
    }) { padding ->
        CompositionLocalProvider(LocalPaddings provides padding) {
            navController.NavigationHost(startDestination, MaterialTheme.animation, Modifier) {
                with(graph.coreModule) {
                    settingsScreen()
                    aboutScreen()
                    mirrorScreen()
                }

                with(graph.wallpapersModule) {
                    mainScreen()
                    wallpaperDetailsScreen()

                    categoriesScreen()
                    categoryDetailsScreen()

                    cacheScreen()
                }

                with(graph.widgetModule) {
                    shapePickerScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalStaggerApi::class)
@Composable
fun BottomBar(currentRoute: String, onClick: (route: String) -> Unit) {
    val stagger = remember {
        staggerListOf(
            { }, false, *quickAccessibleDestinations.toTypedArray()
        )
    }
    val height =
        80.dp + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) WindowInsets.navigationBars.asPaddingValues()
            .calculateBottomPadding() else 0.dp
    LaunchedEffect(key1 = isCompositionLaunched(105)) {
        stagger.animateAsList(this, spec = staggerSpecOf(itemsDelayMillis = 100) {
            visible = true
        })
    }

    val dp80inPx = 80.dp.toPx()
    val barVisible =
        remember(currentRoute) { currentRoute in quickAccessibleDestinations.map { it.destination.path } }
    Surface(
        tonalElevation = 3.dp, modifier = Modifier.animateVisibility(
            barVisible, remember(barVisible) {
                slideVertically(
                    dp80inPx, animationSpec = tween()
                ) + fade(
                    animationSpec = tween(
                        if (barVisible) (300 * 0.35f).toInt() else 300,
                        if (barVisible) (300 * 0.35f).toInt() else 0,
                        if (barVisible) LinearOutSlowInEasing else FastOutLinearInEasing
                    )
                )
            }
        )
    ) {
        Column {
            Row {
                stagger.forEach {
                    val destination = it.value.destination
                    val selected = currentRoute == destination.path
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) onClick(destination.path)
                        },
                        icon = {
                            Icon(
                                imageVector = if (selected) it.value.filledIcon else it.value.outlinedIcon,
                                contentDescription = ""
                            )
                        },
                        label = { Text(text = rememberString(string = it.value.previewName)) },
                        alwaysShowLabel = false,
                        modifier = Modifier
                            .testTag(destination.path)
                            .animateVisibility(
                                it.visible,
                                fade(
                                    animationSpec = tween(
                                        250,
                                        (250 * 0.35f).toInt()
                                    )
                                ) + slideVertically(
                                    from = 80f.dp.toPx(),
                                    animationSpec = tween(250)
                                )
                            )
                            .height(80.dp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height - 80.dp)
            )
        }
    }
}