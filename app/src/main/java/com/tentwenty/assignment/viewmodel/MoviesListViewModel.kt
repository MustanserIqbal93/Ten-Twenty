package com.tentwenty.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ztbl.db.room.lovs.MoviesDao
import com.tentwenty.assignment.model.Movie
import com.tentwenty.assignment.dependencyInjection.API_KEY
import com.tentwenty.assignment.model.ApiEvent
import com.tentwenty.assignment.model.Resource
import com.tentwenty.assignment.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *Created by Mustanser Iqbal
 */
@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val repository: MainRepository,
    private val moviesDataSource: MoviesDao
    //private val moviesDataSource: MoviesDataSource
): ViewModel() {

    private var pageNo: Int = 0
    var moviesList: ArrayList<Movie> = ArrayList()
    lateinit var list: List<Long>
    private val _conversion = MutableStateFlow<ApiEvent>(ApiEvent.Empty)
    val conversion: StateFlow<ApiEvent> = _conversion

    fun getMoviesList(isInternetAvailable: Boolean) {
        if (isInternetAvailable) {
            pageNo += 1
            val queryParams = HashMap<String, String>()
            queryParams["page"] = pageNo.toString()
            queryParams["api_key"] = API_KEY
            viewModelScope.launch(Dispatchers.IO) {
                _conversion.value = ApiEvent.Loading
                when (val quotesResponse = repository.getUpComingMovies(queryParams)) {
                    is Resource.Error -> _conversion.value = ApiEvent.Failure(quotesResponse.message!!)
                    is Resource.Success -> {
                        val quote = quotesResponse.data!!
                        addUpdateDataToList(quote.results)
                        addMoviesInDatabase(quote.results)
                        _conversion.value = ApiEvent.Success
                    }
                }
            }
        }else{
            getMoviesFromDatabase()
        }
    }

    private fun addUpdateDataToList(data: ArrayList<Movie>){
        for (movie in data){
            val existingMovieItem = moviesList.find { m -> movie.id == m.id }
            if(existingMovieItem == null){
                moviesList.add(movie)
            }
        }
    }

    private fun addMoviesInDatabase(data: ArrayList<Movie>) {
        moviesDataSource.insert(data).subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Long>> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(t: List<Long>) {
                }

                override fun onError(e: Throwable) {
                    Log.e("Error", "Error ${e.message}")
                }
            })
    }

    private fun getMoviesFromDatabase() {
        _conversion.value = ApiEvent.Loading
        moviesDataSource.getMoviesList(moviesList.size).subscribeOn(io.reactivex.schedulers.Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Movie>> {

                override fun onSuccess(t: List<Movie>) {
                    if (t.isNotEmpty()) {
                        val movies = (t as ArrayList<Movie>)
                        addUpdateDataToList(movies)
                        _conversion.value = ApiEvent.Success
                    } else {
                        Log.e("Error", "Nothing found in DB")
                        _conversion.value = ApiEvent.Failure("No Data Found")
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    _conversion.value = ApiEvent.Failure(e.message!!)
                }
            })
    }
}