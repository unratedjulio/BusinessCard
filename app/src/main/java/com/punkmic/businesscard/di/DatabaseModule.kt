package com.punkmic.businesscard.di

import android.content.Context
import androidx.room.Room
import com.punkmic.businesscard.data.AppDatabase
import com.punkmic.businesscard.data.BusinessCardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): BusinessCardDao {
        return appDatabase.businessDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "businesscard_db"
        ).build()
    }
}