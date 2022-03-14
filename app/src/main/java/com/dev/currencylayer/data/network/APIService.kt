package com.dev.currencylayer.data.network

import com.dev.currencylayer.data.models.CurrencyTypes
import com.dev.currencylayer.data.models.CurrentExchangeRates
import retrofit2.http.GET

interface APIService {
    @GET("live")
    suspend fun getCurrentExchangeRates(): CurrentExchangeRates

    @GET("list")
    suspend fun getCurrencyTypes(): CurrencyTypes
}