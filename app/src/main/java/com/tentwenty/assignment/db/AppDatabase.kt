package com.tentwenty.assignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ztbl.db.room.lovs.MoviesDao
import com.tentwenty.assignment.model.Movie


@Database(
    entities = [Movie::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun MoviesDao(): MoviesDao
}