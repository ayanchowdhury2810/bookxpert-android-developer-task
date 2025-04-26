package com.android.bookxpert_android_developer_task.ui.screens.LoginScreen

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.bookxpert_android_developer_task.utilities.SignInState
import kotlin.math.log

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = hiltViewModel(),
    state: SignInState,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userData by loginViewModel.userData.collectAsState()

    val isDarkTheme = isSystemInDarkTheme()


//    LaunchedEffect(Unit) {
//        if(userData.data != null){
//            loginViewModel.deleteUserData(userData)
//        }
//    }


    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = onSignInClick, colors = ButtonDefaults.buttonColors(
                containerColor = if (isDarkTheme) Yellow else Red
            )) {
                Text(text = "Sign in", color = if(isDarkTheme) Black else White)
            }
        }
    }


}