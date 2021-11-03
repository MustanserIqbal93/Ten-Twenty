package com.tentwenty.assignment.model.api

import com.tentwenty.assignment.model.MoviesResponse
import com.tentwenty.assignment.model.movieDetail.MovieDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 *Created by Mustanser Iqbal
 */
interface MovieDBApi {
    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@QueryMap queryParams: Map<String, String>): Response<MoviesResponse>
    @GET("movie/{id}")
    suspend fun getMovieDetails(
        @Path(value = "id", encoded = true) id: String, @QueryMap queryParams: Map<String, String>): Response<MovieDetailResponse>

    @GET("movie/{id}/images")
    suspend fun getMovieImages(@Path(value = "id", encoded = true) id: String, @QueryMap queryParams: Map<String, String>): Response<MoviesResponse>

    @GET("movie/{id}/videos")
    suspend fun getMovieVideos(@Path(value = "id", encoded = true) id: String, @QueryMap queryParams: Map<String, String>): Response<MoviesResponse>
}