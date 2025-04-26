package com.android.bookxpert_android_developer_task.ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.bookxpert_android_developer_task.dataClass.SignInResult
import com.android.bookxpert_android_developer_task.dataClass.UserData
import com.android.bookxpert_android_developer_task.ui.screens.HomeScreen.HomeScreen
import com.android.bookxpert_android_developer_task.ui.screens.LoginScreen.LoginScreen
import com.android.bookxpert_android_developer_task.ui.screens.LoginScreen.LoginViewModel
import com.android.bookxpert_android_developer_task.ui.screens.apiScreen.ApiScreen
import com.android.bookxpert_android_developer_task.ui.screens.apiScreen.ApiScreenViewModel
import com.android.bookxpert_android_developer_task.ui.screens.pdfScreen.PdfScreen
import com.android.bookxpert_android_developer_task.utilities.GoogleAuthClient
import com.android.bookxpert_android_developer_task.utilities.SignInState
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.android.bookxpert_android_developer_task.ui.screens.HomeScreen.HomeScreenViewModel
import com.android.bookxpert_android_developer_task.utilities.SessionManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun NavGraph(
    startDestination: String = Destinations.LOGIN_SCREEN,
    navController: NavHostController,
    navigationActions: NavigationActions,
    loginViewModel: LoginViewModel,
    apiScreenViewModel: ApiScreenViewModel,
    homeScreenViewModel: HomeScreenViewModel,
    googleAuthUiClient: GoogleAuthClient,
    launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
) {
    val context = LocalContext.current
    val sessionManager = remember {
        SessionManager(context)
    }
    val isLoginCompletedData = remember { mutableStateOf(sessionManager.getIsLoginCompletedData("is_login_complete", false)) }

    NavHost(
        navController = navController,
        startDestination = if(isLoginCompletedData.value) Destinations.HOME_SCREEN else startDestination,
        modifier = Modifier
    ) {

        Log.d("isLoginCompletedDataisLoginCompletedData", "isLoginCompletedData => ${isLoginCompletedData.value}")

        composable(route = Destinations.LOGIN_SCREEN) {
            val state: State<SignInState> = loginViewModel.state.collectAsStateWithLifecycle()
            val signInResult by loginViewModel.signInResult.observeAsState()
            val coroutineScope = rememberCoroutineScope()

            LaunchedEffect(key1 = state.value.isSignInSuccessful) {
                if(state.value.isSignInSuccessful) {
                    sessionManager.saveIsLoginCompletedData("is_login_complete", true)
                    Toast.makeText(
                        context,
                        "Sign in successful",
                        Toast.LENGTH_LONG
                    ).show()
                    navigationActions.navigateToHome()
                    loginViewModel.insertUserData(
                        SignInResult(
                            data = UserData(
                                userId = signInResult?.data?.userId,
                                userName = signInResult?.data?.userName,
                                profilePictureUrl = signInResult?.data?.profilePictureUrl
                            ),
                            errorMessage = null
                        )
                    )
                    loginViewModel.resetState()
                }
            }
            LoginScreen(
                loginViewModel = loginViewModel,
                state = state.value,
                onSignInClick = {
                    coroutineScope.launch {
                        val signInIntentSender = googleAuthUiClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            )
        }

        composable(route = Destinations.HOME_SCREEN) {
            val coroutineScope = rememberCoroutineScope()
            val userData by homeScreenViewModel.userData.collectAsStateWithLifecycle()
            HomeScreen(
                homeScreenViewModel = homeScreenViewModel,
                onPdfClick = {
                    navigationActions.navigateToPdfScreen()
                },
                onApiClick = {
                    navigationActions.navigateToApiScreen()
                },
                onLogoutClick = {
                    coroutineScope.launch {
                        try {
                            userData?.let { user ->
                                homeScreenViewModel.deleteUserData(user)
                            }
                            sessionManager.saveIsLoginCompletedData("is_login_complete", false)
                            navController.navigate(Destinations.LOGIN_SCREEN) {
                                popUpTo(navController.graph.id) {
                                    inclusive = true
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                "Logout failed. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        }

        composable(route = Destinations.API_SCREEN) {
            ApiScreen(apiScreenViewModel = apiScreenViewModel)
        }

        composable(route = Destinations.PDF_SCREEN) {
            PdfScreen()
        }


    }

}