package com.android.bookxpert_android_developer_task.ui

import androidx.navigation.NavHostController

object Destinations {
    const val HOME_SCREEN = "homeScreen"
    const val LOGIN_SCREEN = "loginScreen"
    const val API_SCREEN = "apiScreen"
    const val PDF_SCREEN = "pdfScreen"
}

class NavigationActions(
    navController: NavHostController
) {
    val navigateToLogin: () -> Unit = {
        navController.navigate(Destinations.LOGIN_SCREEN){
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToHome: () -> Unit = {
        navController.navigate(Destinations.HOME_SCREEN){
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToApiScreen: () -> Unit = {
        navController.navigate(Destinations.API_SCREEN){
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigateToPdfScreen: () -> Unit = {
        navController.navigate(Destinations.PDF_SCREEN){
            launchSingleTop = true
            restoreState = true
        }
    }


}