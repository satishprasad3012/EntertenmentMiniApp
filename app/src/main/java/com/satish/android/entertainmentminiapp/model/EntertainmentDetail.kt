package com.satish.android.entertainmentminiapp.model

/**
 *
 * @author satish
 * 28/03/2019
 *
 * **/

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EntertainmentDetail(
    @SerializedName("Search") val searchList: ArrayList<Entertainment>? = null,
    @SerializedName("totalResults") val totalResults: String? = null,
    @SerializedName("Response") val Response: String? = null
) : Parcelable