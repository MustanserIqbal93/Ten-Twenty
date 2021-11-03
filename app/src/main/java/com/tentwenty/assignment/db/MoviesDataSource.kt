package com.example.ztbl.db.room.lovs

import android.util.Log
import com.tentwenty.assignment.model.Movie
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MoviesDataSource @Inject constructor(private val moviesDao: MoviesDao) {

    fun addMoviesList(movies: ArrayList<Movie>): Single<List<Long>> {
        return moviesDao.insert(movies)
    }

    fun getMoviesList(pageNo: Int): Single<List<Movie>> {
        return moviesDao.getMoviesList(pageNo)
    }
    fun getMovieByID(id: Int): Single<Movie> {
        return moviesDao.getMovieById(id)
    }

    fun deleteDuplicateRecord() {

        Completable.fromAction {
            moviesDao.deleteDuplicateRecords()
        }.subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    Log.d("deleted", "success")
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    Log.d("deleted", "failed")
                }

            })

    }

}
