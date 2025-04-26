package com.android.bookxpert_android_developer_task.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.android.bookxpert_android_developer_task.dataClass.SignInResult
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {
    @Insert
    suspend fun insertUserData(data: SignInResult)

    @Delete
    suspend fun deleteUserData(data: SignInResult)

    @Query("Select * FROM userData")
    fun getUserData(): Flow<SignInResult>
}