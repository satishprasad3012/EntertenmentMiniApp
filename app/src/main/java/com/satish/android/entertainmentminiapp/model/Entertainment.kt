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
data class Entertainment(
    @SerializedName("imdbID") val imdbID: String? = null,
    @SerializedName("Title") val Title: String? = null,
    @SerializedName("Year") val Year: String? = null,
    @SerializedName("Type") val Type: String? = null,
    @SerializedName("Poster") val Poster: String? = null,
    var bookmark: Boolean = false
) : Parcelable