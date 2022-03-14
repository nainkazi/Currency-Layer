package com.dev.currencylayer.di.modules

import com.dev.currencylayer.BuildConfig
import com.dev.currencylayer.data.network.APIService
import com.dev.currencylayer.data.network.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private var BASE_URL = "http://api.currencylayer.com/"
    @Singleton
    @Provides
    fun provideBaseUrl() = BASE_URL
    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG){
        val loggingInterceptor =HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiInterceptor())
            .build()
    }else{
        OkHttpClient
            .Builder()
            .build()
    }
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BaseUrl:String): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        //val client = OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor()).addInterceptor(logging).build()
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideCurrencyService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}