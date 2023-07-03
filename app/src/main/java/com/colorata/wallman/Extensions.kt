package com.colorata.wallman

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.net.Uri
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import soup.compose.material.motion.animation.materialFadeThroughIn
import soup.compose.material.motion.animation.materialFadeThroughOut


fun Context.playButtonClick() =
    getSystemService(AudioManager::class.java).playSoundEffect(AudioManager.FX_KEY_CLICK)

fun Context.vibrate(timeMillis: Long = 5) {
    val manager = getSystemService(Vibrator::class.java)
    manager.vibrate(VibrationEffect.createOneShot(timeMillis, VibrationEffect.DEFAULT_AMPLITUDE))
}

var onNavigate by mutableStateOf(false)
fun NavController.navigateTo(to: String) {
    onNavigate = !onNavigate
    popBackStack()
    navigate(to) {
        launchSingleTop = true
        popUpTo(to) { inclusive = true }
    }
}

fun Context.goToUrl(inURL: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(inURL))
    startActivity(intent)
}

@Composable
fun NavController.route() = currentBackStackEntryAsState().value?.destination?.route

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.optimizedPopEnter(
    list: List<String>,
    enterTransition: EnterTransition
) =
    if (this.initialState.destination.route in list && this.targetState.destination.route !in list) enterTransition else materialFadeThroughIn()

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.optimizedEnter(
    list: List<String>,
    enterTransition: EnterTransition
) =
    if (this.initialState.destination.route !in list && this.targetState.destination.route in list) enterTransition else materialFadeThroughIn()

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.optimizedPopExit(
    list: List<String>,
    exitTransition: ExitTransition
) =
    if (this.initialState.destination.route !in list && this.targetState.destination.route in list) exitTransition else materialFadeThroughOut()

@OptIn(ExperimentalAnimationApi::class)
fun AnimatedContentTransitionScope<NavBackStackEntry>.optimizedExit(
    list: List<String>,
    exitTransition: ExitTransition
) =
    if (this.initialState.destination.route in list && this.targetState.destination.route !in list) exitTransition else materialFadeThroughOut()