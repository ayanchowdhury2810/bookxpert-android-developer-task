package com.android.bookxpert_android_developer_task.ui.screens.HomeScreen

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bookxpert_android_developer_task.dataClass.ApiDataItem
import com.android.bookxpert_android_developer_task.dataClass.SignInResult
import com.android.bookxpert_android_developer_task.ui.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: Repository,
    private val resources: Resources
) : ViewModel() {

    val userData = repository.getUserData.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        SignInResult(null, null, 0)
    )

    suspend fun deleteUserData(data: SignInResult) {
        repository.deleteUserData(data)
    }
}