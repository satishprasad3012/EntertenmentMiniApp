package com.satish.android.entertainmentminiapp.network

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/


import com.satish.android.entertainmentminiapp.utility.NETWORK_SUCCESS_CODE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


abstract class RetrofitCallback<T> protected constructor() : Callback<T> {
// we can define gerenric type of response as per requirement, T may be custom generic class

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            val body = response.body()
            val statusCode = response.code()
            if (statusCode == NETWORK_SUCCESS_CODE) {
                onSuccess(body)
            } else {
                onFail(ErrorResponse(statusCode, response.message()))
            }
        } else {
            onFail(ErrorResponse(response.code(), response.message())) // fail with unknown error code
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFail(processFail(t, call))
    }

    abstract fun onSuccess(response: T?)

    abstract fun onFail(response: ErrorResponse?)

}
