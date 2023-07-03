package com.colorata.wallman.ui.screens

import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.colorata.animateaslifestyle.animateVisibility
import com.colorata.animateaslifestyle.fade
import com.colorata.animateaslifestyle.isCompositionLaunched
import com.colorata.animateaslifestyle.material3.isNotCompact
import com.colorata.animateaslifestyle.material3.rememberWindowSize
import com.colorata.animateaslifestyle.slideVertically
import com.colorata.animateaslifestyle.stagger.ExperimentalStaggerApi
import com.colorata.animateaslifestyle.stagger.animateAsList
import com.colorata.animateaslifestyle.stagger.staggerListOf
import com.colorata.animateaslifestyle.stagger.staggerSpecOf
import com.colorata.animateaslifestyle.stagger.toPx
import com.colorata.wallman.arch.Badge
import com.colorata.wallman.arch.LocalGraph
import com.colorata.wallman.arch.LocalNavController
import com.colorata.wallman.arch.LocalPaddings
import com.colorata.wallman.arch.Strings
import com.colorata.wallman.arch.rememberString
import com.colorata.wallman.arch.viewModel
import com.colorata.wallman.navigateTo
import com.colorata.wallman.optimizedEnter
import com.colorata.wallman.optimizedExit
import com.colorata.wallman.optimizedPopEnter
import com.colorata.wallman.optimizedPopExit
import com.colorata.wallman.route
import com.colorata.wallman.ui.MaterialNavGraphBuilder
import com.colorata.wallman.ui.icons.Animation
import com.colorata.wallman.ui.icons.ContentCopy
import com.colorata.wallman.ui.icons.Storage
import com.colorata.wallman.ui.screens.settings.AboutScreen
import com.colorata.wallman.ui.screens.settings.AnimationsScreen
import com.colorata.wallman.ui.screens.settings.CacheScreen
import com.colorata.wallman.ui.screens.settings.MirrorScreen
import com.colorata.wallman.ui.screens.settings.aboutScreen
import com.colorata.wallman.ui.screens.settings.animationsScreen
import com.colorata.wallman.ui.screens.settings.cacheScreen
import com.colorata.wallman.ui.screens.settings.mirrorScreen
import com.colorata.wallman.ui.theme.animation
import com.colorata.wallman.viewmodels.NavigationViewModel
import com.colorata.wallman.viewmodels.SettingsViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import soup.compose.material.motion.animation.materialFadeThroughIn
import soup.compose.material.motion.animation.materialFadeThroughOut
import soup.compose.material.motion.animation.materialSharedAxisXIn
import soup.compose.material.motion.animation.materialSharedAxisXOut
import soup.compose.material.motion.navigation.MaterialMotionNavHost
import soup.compose.material.motion.navigation.composable


@OptIn(
    ExperimentalAnimationApi::class
)
@Composable
fun Navigation() {
    val viewModel = viewModel { NavigationViewModel() }
    val screenConfig = rememberWindowSize()
    val navController = LocalGraph.current.navController
    var useRail by rememberSaveable {
        mutableStateOf(screenConfig.isNotCompact())
    }
    LaunchedEffect(key1 = screenConfig) {
        useRail = screenConfig.isNotCompact()
    }
    CompositionLocalProvider(LocalNavController provides navController) {
        Row(modifier = Modifier.fillMaxSize()) {
            if (useRail) {
                NavigationRail(viewModel)
            }
            Scaffold(bottomBar = {
                if (!useRail) {
                    BottomBar(viewModel)
                }
            }, modifier = Modifier.weight(1f)) { padding ->
                val anim = animation
                CompositionLocalProvider(LocalPaddings provides padding) {
                    MaterialMotionNavHost(
                        navController = navController,
                        startDestination = "Main",
                        modifier = Modifier.weight(1f)
                    ) {
                        val materialBuilder = MaterialNavGraphBuilder(this, anim)
                        materialBuilder.apply {
                            mainScreen()
                            categoriesScreen()
                            settingsScreen()

                            aboutScreen()
                            animationsScreen()
                            cacheScreen()
                            mirrorScreen()

                            categoryDetailsScreen()
                            wallpaperDetailsScreen()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalStaggerApi::class)
@Composable
fun BottomBar(viewModel: NavigationViewModel) {
    val navController = LocalNavController.current
    val route = navController?.currentBackStackEntryAsState()?.value?.destination?.route
    val stagger = remember {
        staggerListOf(
            { Animatable(80f) }, false, *viewModel.items.toTypedArray()
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
        remember(route) { route in viewModel.items.map { it.name } || route == null }
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
                        onClick = { if (route != it.value.name) navController?.navigateTo(it.value.name) },
                        icon = {
                            Icon(
                                imageVector = if (route == it.value.name) it.value.filledIcon else it.value.outlinedIcon,
                                contentDescription = ""
                            )
                        },
                        label = { Text(text = rememberString(string = it.value.previewName)) },
                        alwaysShowLabel = false,
                        modifier = Modifier
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

@Composable
fun NavigationRail(viewModel: NavigationViewModel) {
    val navController = LocalNavController.current
    val route = navController?.route()
    val animList = remember { mutableStateListOf(false, false, false) }
    val scope = rememberCoroutineScope()
    NavigationRail(modifier = Modifier.padding(top = 100.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LaunchedEffect(key1 = true) {
                scope.launch {
                    animList[0] = true
                    delay(50)
                    animList[1] = true
                    delay(50)
                    animList[2] = true
                }
            }
            viewModel.items.forEachIndexed { index, navItem ->
                NavigationRailItem(
                    selected = route == navItem.name,
                    onClick = { navController?.navigateTo(navItem.name) },
                    icon = {
                        Icon(
                            imageVector = if (route == navItem.name) navItem.filledIcon else navItem.outlinedIcon,
                            contentDescription = ""
                        )
                    },
                    label = { Text(text = rememberString(string = navItem.previewName)) },
                    alwaysShowLabel = false,
                    modifier = Modifier
                        .offset(x = -animateDpAsState(targetValue = if (animList[index]) 0.dp else 80.dp).value)
                        .width(80.dp)
                )
            }
        }
    }
}

fun SnapshotStateList<Badge>.clearWithSuspend() {
    CoroutineScope(Dispatchers.IO).launch {
        delay(550)
        clear()
    }
}