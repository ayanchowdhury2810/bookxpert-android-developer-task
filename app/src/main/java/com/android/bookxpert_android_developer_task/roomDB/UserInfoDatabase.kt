package com.android.bookxpert_android_developer_task.roomDB

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.bookxpert_android_developer_task.dataClass.ApiDataItem
import com.android.bookxpert_android_developer_task.dataClass.SignInResult

@Database(entities = [SignInResult::class, ApiDataItem::class], version = 1)
@TypeConverters(DataConverters::class)
abstract class UserInfoDatabase: RoomDatabase() {

    abstract fun userInfoDao(): UserDataDao
    abstract fun apiDataDao(): SpecificationsApiDataDao

}