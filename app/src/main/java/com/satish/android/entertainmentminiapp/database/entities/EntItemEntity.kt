package com.satish.android.entertainmentminiapp.database.entities

/**
 *
 * @author satish
 * 29/03/2019
 *
 * **/

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = EntItemEntity.TABLE_NAME)
data class EntItemEntity(

    @PrimaryKey
    @ColumnInfo(name = IMDB_ID) var imdbID: String,

    @ColumnInfo(name = TITLE) var title: String,

    @ColumnInfo(name = YEAR) var Year: String,

    @ColumnInfo(name = TYPE) var Type: String,

    @ColumnInfo(name = POSTER) var Poster: String,

    @ColumnInfo(name = GENRE) var Genre: String,

    @ColumnInfo(name = IMDB_RATING) var imdbRating: String,

    @ColumnInfo(name = DIRECTOR) var Director: String,

    @ColumnInfo(name = ACTORS) var Actors: String
) {
    companion object {
        const val TABLE_NAME = "bookmark"
        const val IMDB_ID = "imdbID"
        const val TITLE = "Title"
        const val YEAR = "Year"
        const val TYPE = "Type"
        const val POSTER = "Poster"
        const val GENRE = "Genre"
        const val IMDB_RATING = "imdbRating"
        const val DIRECTOR="Director"
        const val ACTORS="Actors"
    }
}