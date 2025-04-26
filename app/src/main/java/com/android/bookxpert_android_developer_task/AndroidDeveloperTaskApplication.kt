package com.android.bookxpert_android_developer_task

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidDeveloperTaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Starting here")
    }

    companion object {
        const val TAG = "AndroidDeveloperTaskApplication"

        private var instance: AndroidDeveloperTaskApplication? = null
        public fun getContext(): Context? {
            return instance
        }
    }
}
