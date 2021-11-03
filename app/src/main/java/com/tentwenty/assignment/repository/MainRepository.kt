package com.tentwenty.assignment.repository

import com.tentwenty.assignment.model.MoviesResponse
import com.tentwenty.assignment.model.Resource
import com.tentwenty.assignment.model.movieDetail.MovieDetailResponse

/**
 *Created by Mustanser Iqbal
 */
interface MainRepository {
    suspend fun getUpComingMovies(queryParams: Map<String, String>): Resource<MoviesResponse>
    suspend fun getMovieDetails(queryParams: Map<String, String>,id:String): Resource<MovieDetailResponse>
    suspend fun getMovieImages(queryParams: Map<String, String>,id:String): Resource<MoviesResponse>
    suspend fun getMovieVideos(queryParams: Map<String, String>,id:String): Resource<MoviesResponse>
}