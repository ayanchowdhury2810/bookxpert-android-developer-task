package com.android.bookxpert_android_developer_task.ui.screens.LoginScreen

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bookxpert_android_developer_task.dataClass.ApiDataItem
import com.android.bookxpert_android_developer_task.dataClass.SignInResult
import com.android.bookxpert_android_developer_task.ui.Repository
import com.android.bookxpert_android_developer_task.utilities.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository,
    private val resources: Resources
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    private var _signInResult = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> = _signInResult

    fun onSignInResult(result: SignInResult) {
        _state.update {
            it.copy(
                isSignInSuccessful = result.data != null,
                signInError = result.errorMessage
            )
        }
        _signInResult.postValue(result)
    }

    fun resetState(){
        _state.update { SignInState() }
    }

    suspend fun insertUserData(data: SignInResult){
        repository.insertUserData(data)
    }

    val userData = repository.getUserData.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SignInResult(null, null, 0)
    )

    suspend fun deleteUserData(data: SignInResult) {
        repository.deleteUserData(data)
    }

}