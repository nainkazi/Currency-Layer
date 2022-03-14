package com.dev.currencylayer.di.modules

import android.content.Context
import androidx.room.Room
import com.dev.currencylayer.data.room.AppDatabase
import com.dev.currencylayer.data.room.RateDao
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
    fun providesRoomDatabase(@ApplicationContext context: Context): AppDatabase {
       return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesRateDao(@ApplicationContext context: Context): RateDao {
        return providesRoomDatabase(context.applicationContext).getRateDao()
    }
}