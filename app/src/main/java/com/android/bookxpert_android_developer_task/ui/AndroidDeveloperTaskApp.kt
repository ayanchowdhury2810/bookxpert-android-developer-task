package com.android.bookxpert_android_developer_task.ui

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.android.bookxpert_android_developer_task.ui.screens.HomeScreen.HomeScreenViewModel
import com.android.bookxpert_android_developer_task.ui.screens.LoginScreen.LoginViewModel
import com.android.bookxpert_android_developer_task.ui.screens.apiScreen.ApiScreenViewModel
import com.android.bookxpert_android_developer_task.ui.theme.BookxpertandroiddevelopertaskTheme
import com.android.bookxpert_android_developer_task.utilities.GoogleAuthClient

@Composable
fun AndroidDeveloperTaskApp(
    navController: NavHostController,
    navigationActions: NavigationActions,
    loginViewModel: LoginViewModel,
    apiScreenViewModel: ApiScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
    googleAuthUiClient: GoogleAuthClient,
) {
    BookxpertandroiddevelopertaskTheme {
        NavGraph(
            navController = navController,
            navigationActions = navigationActions,
            loginViewModel = loginViewModel,
            apiScreenViewModel = apiScreenViewModel,
            homeScreenViewModel = homeScreenViewModel,
            googleAuthUiClient = googleAuthUiClient,
            launcher = launcher

        )
    }

}