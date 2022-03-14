package com.dev.currencylayer.utils

import android.content.res.Resources
import com.dev.currencylayer.BuildConfig
import com.dev.currencylayer.R
import java.util.*

fun Resources.getFlagResource(flagName: String): Int {
    val id = getIdentifier(
        "_${flagName.toLowerCase(Locale.ROOT)}",
        "drawable",
        BuildConfig.APPLICATION_ID
    )
    if (id == 0) {
        return R.drawable._no_flag
    }
    return id
}