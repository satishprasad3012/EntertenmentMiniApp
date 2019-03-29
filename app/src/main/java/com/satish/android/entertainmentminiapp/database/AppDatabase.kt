package com.satish.android.entertainmentminiapp.database

/**
 *
 * @author satish
 * 29/03/2019
 *
 * **/

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.satish.android.entertainmentminiapp.R
import com.satish.android.entertainmentminiapp.database.daos.EntItemDao
import com.satish.android.entertainmentminiapp.database.entities.EntItemEntity


@Database(entities = [EntItemEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(RoomTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarkItemDao(): EntItemDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildRoomDatabase(context).also { instance = it }
            }
        }

        private fun buildRoomDatabase(context: Context): AppDatabase {
            val buildRoom =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, context.getString(R.string.database))
                    .fallbackToDestructiveMigration()

            return buildRoom.build()
        }
    }
}