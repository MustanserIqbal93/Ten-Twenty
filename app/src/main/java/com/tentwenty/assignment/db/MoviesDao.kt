package com.example.ztbl.db.room.lovs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tentwenty.assignment.model.Movie


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: ArrayList<Movie>): io.reactivex.Single<List<Long>>

    @Query("SELECT * FROM Movies limit 20 offset :pageNo")
    fun getMoviesList(pageNo: Int):io.reactivex.Single<List<Movie>>

    @Query("SELECT * FROM Movies WHERE id = :id")
    fun getMovieById(id:Int):io.reactivex.Single<Movie>

    @Query("DELETE FROM Movies")
    fun deleteMovies()

    @Query("DELETE FROM Movies WHERE rowid NOT IN(SELECT MIN(rowid) FROM Movies GROUP BY id)")
    fun deleteDuplicateRecords()

}