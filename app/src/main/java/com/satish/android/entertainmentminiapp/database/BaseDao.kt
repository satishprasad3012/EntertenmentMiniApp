package com.satish.android.entertainmentminiapp.database

/**
 *
 * @author satish
 * 29/03/2019
 *
 * **/

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

interface BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param any the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(any: T)

    /**
     * Insert an array of objects in the database.
     *
     * @param any the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg any: T)

    /**
     * Update an object from the database.
     *
     * @param any the object to be updated
     */
    @Update
    fun update(any: T)

    /**
     * Delete an object from the database
     *
     * @param any the object to be deleted
     */
    @Delete
    fun delete(any: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<T>)
}