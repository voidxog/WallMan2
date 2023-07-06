package com.colorata.wallman.shared

import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
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
import com.colorata.wallman.wallpapers.ui.mainScreen
import com.colorata.wallman.wallpapers.ui.wallpaperDetailsScreen
import soup.compose.material.motion.navigation.MaterialMotionNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = LocalGraph.current.navigationController
    Scaffold(bottomBar = {
        BottomBar()
    }) { padding ->
        val anim = animation
        CompositionLocalProvider(LocalPaddings provides padding) {
            MaterialMotionNavHost(
                navController = navController.composeController,
                startDestination = "Main"
            ) {
                val materialBuilder = MaterialNavGraphBuilder(this, anim)
                materialBuilder.apply {
                    mainScreen()
                    wallpaperDetailsScreen()
                    categoriesScreen()
                    categoryDetailsScreen()

                    settingsScreen()
                    aboutScreen()
                    mirrorScreen()
                    cacheScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalStaggerApi::class)
@Composable
fun BottomBar() {
    val navController = LocalGraph.current.navigationController
    val route by navController.currentPath.collectAsState()
    val stagger = remember {
        staggerListOf(
            { Animatable(80f) }, false, *quickAccessibleDestinations.toTypedArray()
        )
    }
    val height =
        80.dp + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) WindowInsets.navigationBars.asPaddingValues()
            .calculateBottomPadding() else 0.dp
    LaunchedEffect(key1 = isCompositionLaunched(105)) {
        stagger.animateAsList(this, spec = staggerSpecOf(itemsDelayMillis = 100) {
            visible = true
            animationValue.animateTo(0f, animationSpec = tween(500))
        })
    }
    val barVisible =
        remember(route) { route in quickAccessibleDestinations.map { it.name } }
    Surface(
        tonalElevation = 3.dp, modifier = Modifier.animateVisibility(
            barVisible, slideVertically(
                80.dp.toPx(), animationSpec = tween()
            ) + fade(
                animationSpec = tween(
                    if (barVisible) (300 * 0.35f).toInt() else 300,
                    if (barVisible) (300 * 0.35f).toInt() else 0,
                    if (barVisible) LinearOutSlowInEasing else FastOutLinearInEasing
                )
            )
        )
    ) {
        Column {
            Row {
                stagger.forEach {
                    NavigationBarItem(
                        selected = route == it.value.name,
                        onClick = { if (route != it.value.name) navController.resetRootTo(destination(it.value.name)) },
                        icon = {
                            Icon(
                                imageVector = if (route == it.value.name) it.value.filledIcon else it.value.outlinedIcon,
                                contentDescription = ""
                            )
                        },
                        label = { Text(text = rememberString(string = it.value.previewName)) },
                        alwaysShowLabel = false,
                        modifier = Modifier
                            .testTag(it.value.name)
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
                            /*.graphicsLayer(
                                translationY = it.animationValue.value.dp.toPx()
                            )*/
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