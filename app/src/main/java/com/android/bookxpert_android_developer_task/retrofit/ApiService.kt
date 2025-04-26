package com.android.bookxpert_android_developer_task.retrofit

import com.android.bookxpert_android_developer_task.dataClass.ApiData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("objects")
    suspend fun getApiData(): Response<ApiData>
}