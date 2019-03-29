package com.satish.android.entertainmentminiapp.app

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.support.multidex.MultiDexApplication
import com.satish.android.entertainmentminiapp.database.AppDatabase

class EntertainmentApplication : MultiDexApplication() {

    val appDatabase by lazy { AppDatabase.get(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: EntertainmentApplication
    }
}


