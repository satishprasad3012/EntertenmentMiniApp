package com.satish.android.entertainmentminiapp.network

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.app.EntertainmentApplication
import com.satish.android.entertainmentminiapp.utility.*
import retrofit2.Call
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun <T> processFail(e: Throwable, call: Call<T>): ErrorResponse {
    val response = ErrorResponse()
    if (e is UnknownHostException || e is ConnectException) {
        response.message =
            EntertainmentApplication.instance.applicationContext.getString(R.string.no_internet)
        response.statusCode = (NETWORK_CODE_ERROR)
    } else if (e is SocketTimeoutException) {
        response.message =
            EntertainmentApplication.instance.applicationContext.getString(R.string.internet_too_slow)
        response.statusCode = (NETWORK_CODE_SOCKET_TIMEOUT)
    } else if (call.isCanceled) {
        response.message = EntertainmentApplication.instance.applicationContext.getString(R.string.network_call_cancelled)
        response.statusCode = (NETWORK_CODE_CALL_CANCELLED)
    } else {
        try {
            response.message = e.localizedMessage
        } catch (e: Exception) {
            e.log()
        }
    }
    EntertainmentApplication.instance.applicationContext.toast(response.message)
    return response
}
