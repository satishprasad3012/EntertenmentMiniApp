package com.satish.android.entertainmentminiapp.utility

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.satish.android.entertainmentminiapp.network.RetrofitProvider
import com.satish.android.entertainmentminiapp.network.RetrofitServices

fun Context.toast(@StringRes message: Int) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(message: CharSequence?) = message?.let {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Int?.orZero() = this ?: 0


fun isArrayEmpty(arrayList: ArrayList<*>?): Boolean {
    return arrayList == null || arrayList.isEmpty()
}

fun retroServices(): RetrofitServices {
    return RetrofitProvider.instance.services
}


