package com.tentwenty.assignment.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ztbl.db.room.lovs.MoviesDao
import com.tentwenty.assignment.dependencyInjection.API_KEY
import com.tentwenty.assignment.model.ApiEvent
import com.tentwenty.assignment.model.MovieImage
import com.tentwenty.assignment.model.Movie
import com.tentwenty.assignment.model.Resource
import com.tentwenty.assignment.model.movieDetail.MovieDetailResponse
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
class MovieDetailsViewModel @Inject constructor(
    private val repository: MainRepository,
    private val moviesDataSource: MoviesDao
) : ViewModel() {

    var moviesList: ArrayList<Movie> = ArrayList()
    var trailerList: ArrayList<Movie> = ArrayList()
    var imagesList: ArrayList<MovieImage> = ArrayList()

    lateinit var movieDetails: MovieDetailResponse

    private val _conversion = MutableStateFlow<ApiEvent>(ApiEvent.Empty)
    private val _MediaLoader = MutableStateFlow<ApiEvent>(ApiEvent.Empty)
    val conversion: StateFlow<ApiEvent> = _conversion
    val imageLoader: StateFlow<ApiEvent> = _MediaLoader

    fun getMovieDetails(id: String) {
        val queryParams = HashMap<String, String>()
        queryParams["api_key"] = API_KEY
        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = ApiEvent.Loading
            when (val quotesResponse = repository.getMovieDetails(queryParams, id)) {
                is Resource.Error -> _conversion.value = ApiEvent.Failure(quotesResponse.message!!)
                is Resource.Success -> {
                    val quote = quotesResponse.data!!
                    movieDetails = quote
                    _conversion.value = ApiEvent.Success
                }
            }
        }

    }

    fun getMovieImages(id: String) {

        val queryParams = HashMap<String, String>()
        queryParams["api_key"] = API_KEY
        viewModelScope.launch(Dispatchers.IO) {
            _conversion.value = ApiEvent.Loading
            when (val quotesResponse = repository.getMovieImages(queryParams, id)) {
                is Resource.Error -> _MediaLoader.value = ApiEvent.Failure(quotesResponse.message!!)
                is Resource.Success -> {
                    val quote = quotesResponse.data!!
                    imagesList = quote.backdrops
                    _MediaLoader.value = ApiEvent.Success
                    getMovieVideos(id)
                }
            }
        }

    }

    private fun getMovieVideos(id: String) {

        val queryParams = HashMap<String, String>()
        queryParams["api_key"] = API_KEY
        viewModelScope.launch(Dispatchers.IO) {
            when (val quotesResponse = repository.getMovieVideos(queryParams, id)) {
                is Resource.Error -> _MediaLoader.value = ApiEvent.Failure(quotesResponse.message!!)
                is Resource.Success -> {
                    val quote = quotesResponse.data!!
                    trailerList = quote.results
                    getMovieDetails(id)
                }
            }
        }
    }

    private fun addUpdateDataToList(data: ArrayList<Movie>) {
        for (movie in data) {
            val existingMovieItem = moviesList.find { m -> movie.id == m.id }
            if (existingMovieItem == null) {
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