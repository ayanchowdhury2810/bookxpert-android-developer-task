package com.android.bookxpert_android_developer_task.roomDB

import androidx.room.TypeConverter
import com.android.bookxpert_android_developer_task.dataClass.Data
import com.android.bookxpert_android_developer_task.dataClass.UserData
import com.google.gson.Gson

class DataConverters {

    @TypeConverter
    fun fromUserData(userData: UserData?): String? {
        return if (userData == null) null else Gson().toJson(userData)
    }

    @TypeConverter
    fun toUserData(userDataString: String?): UserData? {
        return if (userDataString == null) null else Gson().fromJson(
            userDataString,
            UserData::class.java
        )
    }

    @TypeConverter
    fun fromApiData(data: Data?): String? {
        return if (data == null) null else Gson().toJson(data)
    }

    @TypeConverter
    fun toApiData(data: String?): Data? {
        return if (data == null) null else Gson().fromJson(
            data,
            Data::class.java
        )
    }
}