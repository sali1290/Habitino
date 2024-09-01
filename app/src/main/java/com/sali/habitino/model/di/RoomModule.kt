package com.sali.habitino.model.di

import android.content.Context
import androidx.room.Room
import com.sali.habitino.model.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDB(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "habitino-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSelfAddedHabitDao(appDatabase: AppDatabase) = appDatabase.selfAddedHabitDao()

    @Provides
    @Singleton
    fun provideHabitDao(appDatabase: AppDatabase) = appDatabase.habitDao()

    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase) = appDatabase.appTrackDao()

}