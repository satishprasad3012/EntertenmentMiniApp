package com.satish.android.entertainmentminiapp.utility

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.util.Log
import com.satish.android.entertainmentminiapp.BuildConfig

val isLog = BuildConfig.DEBUG

private fun Any.tag(): String {
    return try {
        if (this::class.java.simpleName.isNullOrEmpty()) "Empty" else this::class.java.simpleName
    } catch (e: Exception) {
        "EmptyTag"
    }
}

fun java.lang.Exception.log() {
    if (isLog) {
        Log.e(tag(), "", this)
    }
}