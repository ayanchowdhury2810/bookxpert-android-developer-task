package com.android.bookxpert_android_developer_task.ui

import android.util.Log
import com.android.bookxpert_android_developer_task.dataClass.ApiData
import com.android.bookxpert_android_developer_task.dataClass.ApiDataItem
import com.android.bookxpert_android_developer_task.dataClass.SignInResult
import com.android.bookxpert_android_developer_task.retrofit.ApiService
import com.android.bookxpert_android_developer_task.roomDB.SpecificationsApiDataDao
import com.android.bookxpert_android_developer_task.roomDB.UserDataDao
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDataDao,
    private val specificationsApiDataDao: SpecificationsApiDataDao
) {

    suspend fun getApiData(): ApiData? {
        return try {
            val response = apiService.getApiData()
            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else if (response.code() == 400) {
                val responseErrorBody = response.errorBody()!!.string()
                val jsonObject = JSONObject(responseErrorBody)
                val message = jsonObject.getString("message")
                Log.d("getTodos", " response code = 400" + response.errorBody()!!.string())
                return ApiData()
            } else if (response.code() == 403) {
                Log.d("getTodos", " response code = 403")
                return ApiData()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.d("getTodos", " Exception" + e.toString())
            null
        }
    }

    suspend fun insertUserData(data: SignInResult) {
        userDao.insertUserData(data)
    }

    suspend fun deleteUserData(data: SignInResult) {
        userDao.deleteUserData(data)
    }

    val getUserData: Flow<SignInResult> = userDao.getUserData()

    suspend fun insertSpecsData(data: ApiDataItem){
        specificationsApiDataDao.insertSpecsData(data)
    }

    suspend fun deleteSpecsData(data: ApiDataItem){
        specificationsApiDataDao.deleteSpecsData(data)
    }

    suspend fun updateSpecsData(data: ApiDataItem){
        specificationsApiDataDao.updateSpecsData(data)
    }

    val getSpecsData = specificationsApiDataDao.getAllSpecsData()

}