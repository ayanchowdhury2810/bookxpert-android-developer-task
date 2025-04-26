package com.android.bookxpert_android_developer_task.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class SignInResult (
    val data: UserData? = null,
    val errorMessage: String? = null,
    @PrimaryKey
    val id: Int = 1
)

data class UserData(
    val userId: String?,
    val userName: String?,
    val profilePictureUrl: String?
)