package com.android.bookxpert_android_developer_task.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.android.bookxpert_android_developer_task.ui.screens.HomeScreen.HomeScreenViewModel
import com.android.bookxpert_android_developer_task.ui.screens.LoginScreen.LoginViewModel
import com.android.bookxpert_android_developer_task.ui.screens.apiScreen.ApiScreenViewModel
import com.android.bookxpert_android_developer_task.utilities.GoogleAuthClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val apiScreenViewModel: ApiScreenViewModel by viewModels()
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()
    private val googleAuthUiClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {task->
            if(!task.isSuccessful){
                return@OnCompleteListener
            }

            val token = task.result

            Log.d("TOKEN", "token-> $token")

            Log.d("SendNotification", "Sending notification with token: $token")
        })

        enableEdgeToEdge()
        setContent {
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionsGranted ->

                permissionsGranted.forEach { (permission, granted) ->
                    if (granted) {
                        Log.d("permission", "$permission granted")

                    } else {
                        Log.d("permission", "$permission denied")
                    }
                }
            }

            val permissions = listOf(
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.POST_NOTIFICATIONS
            )


            LaunchedEffect(Unit) {
                permissionLauncher.launch(permissions.toTypedArray())
            }

            val navController = rememberNavController()
            val navigationActions = remember(navController) {
                NavigationActions(navController)
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.getSignInResultFromIntent(
                                intent = result.data ?: return@launch
                            )
                            loginViewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            AndroidDeveloperTaskApp(
                navController,
                navigationActions,
                loginViewModel,
                apiScreenViewModel,
                homeScreenViewModel,
                launcher,
                googleAuthUiClient
            )
        }
    }
}