package com.android.bookxpert_android_developer_task.dataClass

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "specificationsApiData")
data class ApiDataItem(
    val `data`: Data?,
    val id: String?,
    val name: String?,
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0
)