package com.dev.currencylayer

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dev.currencylayer.data.models.Currency
import com.dev.currencylayer.data.models.CurrencyTypes
import com.dev.currencylayer.data.network.APIService
import com.dev.currencylayer.data.repositories.Repository
import com.dev.currencylayer.data.room.RateDao
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class RepositoryTest :TestCase(){
    val apiService = mockk<APIService>()
    val rateDao = mockk<RateDao>()
    val sharedPreferences = mockk<SharedPreferences>()
    lateinit var repository: Repository
    private val mainThreadSurrogate = newSingleThreadContext("Default thread")

    @Before
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(mainThreadSurrogate)
        repository = Repository(apiService, rateDao, sharedPreferences)
    }

    @Test
    fun testPutDataInDB(){
        var currency = Currency()
        currency.amount = "123"
        currency.code = "PKR"
        currency.name = "Pakistan"
        currency.rate = 1.0
        var list = ArrayList<Currency>()
        list.add(currency)
        GlobalScope.launch(Dispatchers.Default) {
            repository.updateAllExchangeRates(list)
            var result = repository.getSavedExchangeRates()
            assert(result.value?.get(0)?.amount == list[0].amount)
            assert(result.value?.get(0)?.code == list[0].code)
            assert(result.value?.get(0)?.name == list[0].name)
            assert(result.value?.get(0)?.rate == list[0].rate)
        }

    }
    @Test
    fun testCheckDbData() {
          every { rateDao.getAllList() }.returns(MutableLiveData())
          GlobalScope.launch(Dispatchers.Default) {
            var result = repository.getSavedExchangeRates()
            assert(result.value != null)
        }

    }

    @After
    public override fun tearDown() {
        super.tearDown()
        mainThreadSurrogate.close()
    }

}