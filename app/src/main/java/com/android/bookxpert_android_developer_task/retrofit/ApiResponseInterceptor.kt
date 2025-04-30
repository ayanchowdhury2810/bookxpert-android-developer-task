package com.android.bookxpert_android_developer_task.retrofit

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.bookxpert_android_developer_task.AndroidDeveloperTaskApplication
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import java.io.IOException

class ApiResponseInterceptor: Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (response.code == 200) {
            try {
                val contentType = response.body?.contentType()
                val jsonObject = JSONObject(response.body?.string().orEmpty())

                val body = jsonObject.toString().toResponseBody(contentType)
                return response.newBuilder().body(body).build()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (response.code == 403) {

            //Handle 403 Response
            try {
                Log.e("response.Code - 403", "" + response.body.toString())
                val contentType = response.body?.contentType()
                Log.e("okhttp - contentType - 403", contentType.toString())

                val jsonObject = JSONObject(response.body?.string().orEmpty())
                Log.e("okhttp - response body - 403", jsonObject.toString())

                val body = jsonObject.toString().toResponseBody(contentType)
//                SessionManager.logout()

                try
                {
                    AndroidDeveloperTaskApplication.getContext()
                        ?.let {
                            val localBroadcastManager = LocalBroadcastManager.getInstance(it)
                            val intent = Intent("force-logout")
                            intent.putExtra("message", "Session Expire")
                            localBroadcastManager.sendBroadcast(intent)
                        }
                }
                catch (ex: Exception)
                {
                    Log.e("send Broadcast",""+ex.toString())
                }


                return response.newBuilder().body(body).build()

            } catch (exec: java.lang.Exception) {
                Log.e("403 Exception:", "" + exec.message)
            }
        }else if (response.code == 400) {

            //Handle 400 Response
            try {
                Log.e("response.Code - 400", "" + response.body.toString())
                val contentType = response.body?.contentType()
                Log.e("okhttp - contentType - 400", contentType.toString())

                val jsonObject = JSONObject(response.body?.string().orEmpty())
                Log.e("okhttp - response body - 400", jsonObject.toString())

                val body = jsonObject.toString().toResponseBody(contentType)

                return response.newBuilder().body(body).build()

            } catch (exec: java.lang.Exception) {
                Log.e("400 Exception:", "" + exec.message)
            }
        }
        return response
    }
}
