package com.android.bookxpert_android_developer_task.roomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.bookxpert_android_developer_task.dataClass.ApiDataItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecificationsApiDataDao {
    @Insert
    suspend fun insertSpecsData(note: ApiDataItem)

    @Delete
    suspend fun deleteSpecsData(note: ApiDataItem)

    @Update
    suspend fun updateSpecsData(note: ApiDataItem)

    @Query("SELECT * FROM specificationsApiData")
    fun getAllSpecsData(): Flow<List<ApiDataItem>>
}