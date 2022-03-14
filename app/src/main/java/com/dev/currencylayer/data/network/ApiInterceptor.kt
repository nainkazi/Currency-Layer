package com.dev.currencylayer.data.network

import com.dev.currencylayer.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url =
            original.url.newBuilder().addQueryParameter("access_key", BuildConfig.API_KEY).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}