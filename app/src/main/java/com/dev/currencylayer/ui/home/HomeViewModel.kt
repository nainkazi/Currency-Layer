package com.dev.currencylayer.ui.home

import androidx.lifecycle.*
import com.dev.currencylayer.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
     private val period = 30 * 60 * 1000
     val currencyData = repository.getSavedExchangeRates()
    init {
        if((repository.getLastDataFetchTime() + period) < repository.getCurrentTimeStampInMillis()){
            viewModelScope.async {
                repository.syncCurrencyRates()
            }
        }
    }

}