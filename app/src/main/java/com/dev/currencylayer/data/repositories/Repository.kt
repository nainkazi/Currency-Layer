package com.dev.currencylayer.data.repositories

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.dev.currencylayer.data.models.Currency
import com.dev.currencylayer.data.models.CurrencyTypes
import com.dev.currencylayer.data.models.CurrentExchangeRates
import com.dev.currencylayer.data.network.APIService
import com.dev.currencylayer.data.room.RateDao
import kotlinx.coroutines.delay
import javax.inject.Inject

class Repository @Inject constructor(
    private val service: APIService,
    private val rateDao: RateDao,
    private val sharedPreferences: SharedPreferences
) {

    private val savedTimeStamp = "fetch_timestamp"

    // Remote
    private suspend fun getCurrentExchangeRates() = service.getCurrentExchangeRates()
    private suspend fun getCurrencyTypes() = service.getCurrencyTypes()

    // Local
    fun getSavedExchangeRates(): LiveData<List<Currency>> = rateDao.getAllList()
    suspend fun updateAllExchangeRates(saveList: List<Currency>) = rateDao.updateAllRate(saveList)

    suspend fun syncCurrencyRates(){
        val period = 30 * 60 * 1000
        while (true) {
            val rates = getCurrentExchangeRates()
            val currencies = getCurrencyTypes()
            val data = assembleCurrencyData(rates, currencies)
            if(data.size > 0){
               setDataFetchTime(getCurrentTimeStampInMillis())
            }
            updateAllExchangeRates(data)
            delay(period.toLong())
        }
    }

    fun assembleCurrencyData(
        exchangeRates: CurrentExchangeRates,
        currencyTypes: CurrencyTypes
    ): ArrayList<Currency> {
        val list = ArrayList<Currency>()
        for (entry in currencyTypes.currencies) {
            val currency = Currency()
            currency.code = entry.key
            currency.name = entry.value
            currency.rate =
                exchangeRates.quotes[exchangeRates.source + currency.code]!!
            list.add(currency)
        }
        return list
    }

     fun getLastDataFetchTime(): Long {
        return sharedPreferences.getLong(savedTimeStamp, -1)
    }

    private fun setDataFetchTime(timestamp: Long) {
        sharedPreferences.edit().putLong(savedTimeStamp, timestamp).apply()
    }

    fun getCurrentTimeStampInMillis(): Long {
        return System.currentTimeMillis()
    }
}


