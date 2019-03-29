package com.satish.android.entertainmentminiapp.database

/**
 *
 * @author satish
 * 29/03/2019
 *
 * **/

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.satish.android.entertainmentminiapp.model.EntDetail
import java.util.*

class RoomTypeConverter {

    @TypeConverter
    fun fromArray(list: ArrayList<Any>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(value: String): ArrayList<String>? {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson<ArrayList<String>>(value, listType)
    }

    @TypeConverter
    fun toEntList(data: String?): List<EntDetail> {
        if (data == null) return Collections.emptyList()
        val listType = object : TypeToken<List<EntDetail>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun fromEntList(data: List<EntDetail>): String {
        return Gson().toJson(data)
    }


    @TypeConverter
    fun toEntItem(data: String): EntDetail? {
        val itemType = object : TypeToken<EntDetail>() {}.type
        return Gson().fromJson(data, itemType)
    }

    @TypeConverter
    fun fromEntItem(data: EntDetail?): String {
        return if (data != null) Gson().toJson(data) else ""
    }

}