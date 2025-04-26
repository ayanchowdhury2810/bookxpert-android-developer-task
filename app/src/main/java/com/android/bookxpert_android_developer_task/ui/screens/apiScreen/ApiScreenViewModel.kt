package com.android.bookxpert_android_developer_task.ui.screens.apiScreen

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.bookxpert_android_developer_task.dataClass.ApiData
import com.android.bookxpert_android_developer_task.dataClass.ApiDataItem
import com.android.bookxpert_android_developer_task.retrofit.Resource
import com.android.bookxpert_android_developer_task.ui.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiScreenViewModel @Inject constructor(
    private val repository: Repository,
    private val resources: Resources
) : ViewModel() {

    private var _apiResult = MutableLiveData<Resource<ApiData>>()
    val apiResult: LiveData<Resource<ApiData>> = _apiResult

    private val _editItem = MutableLiveData<String>()
    val editItem: LiveData<String> = _editItem

    val allSpecsData: StateFlow<List<ApiDataItem>> = repository.getSpecsData.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun updateEditItem(item: String) {
        Log.d("AAAAAAAA", "VVVVVVVVVVV")
        _editItem.value = item
    }


    fun getApiData() {
        viewModelScope.launch(Dispatchers.IO) {
            _apiResult.postValue(Resource.loading(null))
            val response = repository.getApiData()
            if (response != null) {
                _apiResult.postValue(Resource.success(response))
                if (!response.isNullOrEmpty()) {
                    Log.d("AYAN ", "item=> ${response}")
                    response.forEach {
                        Log.d("AYAN ", "item=> $it")
                        it?.let { item ->
                            insertSpecsData(item)
                        }
                    }
                }
            } else {
                _apiResult.postValue(Resource.error("Something went wrong", null))
            }
        }
    }


    suspend fun insertSpecsData(data: ApiDataItem) {
        repository.insertSpecsData(data)
    }

    suspend fun deleteSpecsData(data: ApiDataItem) {
        repository.deleteSpecsData(data)
    }

    suspend fun updateSpecsData(data: ApiDataItem) {
        repository.updateSpecsData(data)
    }


}