package com.tentwenty.assignment.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Movies", indices = [Index(value = ["id"], unique = true)])
data class Movie(

    @PrimaryKey(autoGenerate = true) var dbId: Long = 0,
    val adult: Boolean?,
    val backdrop_path: String?,
    val genre_ids: MutableList<Int>?,
    val id: String,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
){
    @Ignore var name: String? = null
    @Ignore var key: String? = null
}