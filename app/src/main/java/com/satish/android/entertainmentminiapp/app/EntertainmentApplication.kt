package com.satish.android.entertainmentminiapp.app

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.support.multidex.MultiDexApplication

class EntertainmentApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: EntertainmentApplication
    }
}


