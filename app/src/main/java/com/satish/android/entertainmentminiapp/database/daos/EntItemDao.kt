package com.satish.android.entertainmentminiapp.database.daos

/**
 *
 * @author satish
 * 29/03/2019
 *
 * **/

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.satish.android.entertainmentminiapp.database.BaseDao
import com.satish.android.entertainmentminiapp.database.entities.EntItemEntity

@Dao
interface EntItemDao : BaseDao<EntItemEntity> {

    @Query("SELECT * FROM " + EntItemEntity.TABLE_NAME)
    fun getAllBookmarkItems(): List<EntItemEntity>?

    @Query("SELECT * FROM " + EntItemEntity.TABLE_NAME + " WHERE " + EntItemEntity.IMDB_ID + " = :imdbId")
    fun getBookmarkItem(imdbId: String): EntItemEntity?

    @Query("DELETE FROM " + EntItemEntity.TABLE_NAME + " WHERE " + EntItemEntity.IMDB_ID + " = :imdbId")
    fun deleteBookmark(imdbId: String)

    @Query("DELETE FROM " + EntItemEntity.TABLE_NAME)
    fun clearData()
}