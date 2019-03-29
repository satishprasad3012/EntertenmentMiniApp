package com.satish.android.entertainmentminiapp.network

import com.satish.android.entertainmentminiapp.model.EntertainmentDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author satish
 * 28/03/2019
 *
 * here need to add all API call methods
 *
 * ***/


interface RetrofitServices {

    @GET("/")
    fun getEntSearchList(
        @Query("s") searchkey: String,
        @Query("page") page: Int,
        @Query("apikey") apikey: String
    ): Call<EntertainmentDetail>

}