package com.tentwenty.assignment.repository

import com.tentwenty.assignment.model.MoviesResponse
import com.tentwenty.assignment.model.api.MovieDBApi
import com.tentwenty.assignment.model.Resource
import com.tentwenty.assignment.model.movieDetail.MovieDetailResponse
import java.lang.Exception
import javax.inject.Inject

/**
 *Created by Mustanser Iqbal
 */
class DefaultMainRepository @Inject constructor(
    val movieDBApi: MovieDBApi
):MainRepository {
    override suspend fun getUpComingMovies(queryParams: Map<String, String>): Resource<MoviesResponse> {
        return  try {
            val response = movieDBApi.getUpComingMovies(queryParams)

            val serverResponse = response.body()
            if (response.code() == 200 && serverResponse != null) {
                Resource.Success(serverResponse)
            } else {
                Resource.Error("An Error occurred")
            }
        } catch (e: Exception) {
            println("MovieDBApi $e")
            Resource.Error("An $e occurred")
        }
    }


    override suspend fun getMovieDetails(queryParams: Map<String, String>,id:String): Resource<MovieDetailResponse> {
        return  try {
            val response = movieDBApi.getMovieDetails(id,queryParams)

            val serverResponse = response.body()
            if (response.code() == 200 && serverResponse != null) {
                Resource.Success(serverResponse)
            } else {
                Resource.Error("An Error occurred")
            }
        } catch (e: Exception) {
            println("MovieDBApi $e")
            Resource.Error("An $e occurred")
        }
    }

    override suspend fun getMovieImages(queryParams: Map<String, String>,id:String): Resource<MoviesResponse> {
        return  try {
            val response = movieDBApi.getMovieImages(id,queryParams)

            val serverResponse = response.body()
            if (response.code() == 200 && serverResponse != null) {
                Resource.Success(serverResponse)
            } else {
                Resource.Error("An Error occurred")
            }
        } catch (e: Exception) {
            println("MovieDBApi $e")
            Resource.Error("An $e occurred")
        }
    }

    override suspend fun getMovieVideos(queryParams: Map<String, String>,id:String): Resource<MoviesResponse> {
        return  try {
            val response = movieDBApi.getMovieVideos(id,queryParams)

            val serverResponse = response.body()
            if (response.code() == 200 && serverResponse != null) {
                Resource.Success(serverResponse)
            } else {
                Resource.Error("An Error occurred")
            }
        } catch (e: Exception) {
            println("MovieDBApi $e")
            Resource.Error("An $e occurred")
        }
    }
}