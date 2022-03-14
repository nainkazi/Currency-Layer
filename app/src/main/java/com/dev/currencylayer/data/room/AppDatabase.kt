package com.dev.currencylayer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev.currencylayer.data.models.Currency


@Database(entities = [Currency::class], exportSchema = false,version = AppDatabase.VERSION)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
        const val NAME = "app-db"
    }

    abstract fun getRateDao(): RateDao
}