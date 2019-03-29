package com.satish.android.entertainmentminiapp.utility

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer


fun <T> LiveData<T>.observeNullable(owner: LifecycleOwner, observer: (T?) -> Unit) =
    observe(owner, Observer<T> { v -> observer.invoke(v) })

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    this.observe(owner, Observer {
        it?.let {
            observer(it)
        }
    })
}
