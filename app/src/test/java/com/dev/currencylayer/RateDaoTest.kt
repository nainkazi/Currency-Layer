package com.dev.currencylayer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dev.currencylayer.data.models.Currency
import com.dev.currencylayer.data.room.AppDatabase
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
@ExperimentalCoroutinesApi
class RateDaoTest : TestCase() {
    lateinit var appDatabase:AppDatabase
    private val mainThreadSurrogate = newSingleThreadContext("Default thread")
    @Before
    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(mainThreadSurrogate)
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Test
    fun putDatainDB(){
        var currency = Currency()
        currency.amount = "123"
        currency.code = "PKR"
        currency.name = "Pakistan"
        currency.rate = 1.0
        var list = ArrayList<Currency>()
        list.add(currency)
        GlobalScope.launch(Dispatchers.Default){
            appDatabase.getRateDao().updateAllRate(list)
            val result = appDatabase.getRateDao().getAllList()
            assert(result.value?.get(0)?.amount == list[0].amount)
            assert(result.value?.get(0)?.code == list[0].code)
            assert(result.value?.get(0)?.name == list[0].name)
            assert(result.value?.get(0)?.rate == list[0].rate)
        }

    }

    @After
    public override fun tearDown() {
        super.tearDown()
        mainThreadSurrogate.close()

    }


}