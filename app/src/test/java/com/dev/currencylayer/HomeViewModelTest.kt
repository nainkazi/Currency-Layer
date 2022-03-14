package com.dev.currencylayer


import androidx.lifecycle.MutableLiveData
import com.dev.currencylayer.data.models.Currency
import com.dev.currencylayer.data.repositories.Repository
import com.dev.currencylayer.ui.home.HomeViewModel
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class HomeViewModelTest : TestCase(){
    private lateinit var viewModel: HomeViewModel
    val repository:Repository = mockkClass(Repository::class)
    private val mainThreadSurrogate = newSingleThreadContext("Default thread")

    @Before
     public override fun setUp() {
         Dispatchers.setMain(mainThreadSurrogate)
         every { repository.getSavedExchangeRates() } returns MutableLiveData<List<Currency>>()
         viewModel = HomeViewModel(repository)
    }

    @After
    public override fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun syncCurrencyRates_verifyCalled() {
        coVerify { repository.syncCurrencyRates() }
    }

    @Test
    fun getSavedExchangeRates_verifyCalled() {
        coVerify { repository.getSavedExchangeRates() }
    }

    }
