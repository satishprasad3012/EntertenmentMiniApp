package com.satish.android.entertainmentminiapp.utility

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.provider.Settings
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.satish.android.entertainmentminiapp.app.EntertainmentApplication
import com.satish.android.entertainmentminiapp.app.GlideApp
import java.util.*

val deviceId: String
    get() = Settings.System.getString(
        EntertainmentApplication.instance.applicationContext.contentResolver,
        Settings.Secure.ANDROID_ID
    )

val defaultLocale: String
    get() {
        try {
            return Locale.getDefault().toString()
        } catch (ignored: Exception) {

        }

        return ""
    }

fun glide(view: ImageView, imageUrl: String?) {

    if (isValidContextForGlide(view.context)) {
        try {
            GlideApp.with(view.context)
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        } catch (e: Exception) {

        }
    }
}

private fun isValidContextForGlide(context: Context?): Boolean {
    if (context == null) return false
    if (context is Activity) {
        val activity = context as Activity?
        if (activity?.isDestroyed == true || activity?.isFinishing == true) return false
    }
    return true
}

val isNetworkAvailable: Boolean
    get() = isNetworkAvailable(EntertainmentApplication.instance)

fun isNetworkAvailable(context: Context): Boolean {
    val mCM = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = mCM.activeNetworkInfo
    return netInfo != null && netInfo.isConnectedOrConnecting
}
