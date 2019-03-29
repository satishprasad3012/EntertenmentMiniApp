package com.satish.android.entertainmentminiapp.model

/**
 *
 * @author satish
 * 29/03/2019
 *
 * **/

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class EntDetail(
    @SerializedName("imdbID") val imdbID: String? = null,
    @SerializedName("Title") val Title: String? = null,
    @SerializedName("Year") val Year: String? = null,
    @SerializedName("Type") val Type: String? = null,
    @SerializedName("Poster") val Poster: String? = null,
    @SerializedName("Genre") val Genre: String? = null,
    @SerializedName("imdbRating") val imdbRating: String? = null,
    @SerializedName("Director") val Director: String? = null,
    @SerializedName("Actors") val Actors: String? = null
) : Parcelable