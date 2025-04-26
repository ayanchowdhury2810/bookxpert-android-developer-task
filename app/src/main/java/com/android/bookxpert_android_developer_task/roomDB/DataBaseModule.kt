package com.android.bookxpert_android_developer_task.roomDB

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): UserInfoDatabase {
        return Room.databaseBuilder(
            context,
            UserInfoDatabase::class.java,
            "user_info_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(database: UserInfoDatabase): UserDataDao {
        return database.userInfoDao()
    }

    @Provides
    fun provideSpecificationDao(database: UserInfoDatabase): SpecificationsApiDataDao {
        return database.apiDataDao()
    }
}
