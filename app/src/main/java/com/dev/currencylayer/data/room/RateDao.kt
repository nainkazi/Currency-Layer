package com.dev.currencylayer.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev.currencylayer.data.models.Currency


@Dao
interface RateDao {

    @Query("SELECT * from currencyTable")
    fun getAllList(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAllRate(rateList: List<Currency>)
}