package com.tentwenty.assignment.dependencyInjection

import com.tentwenty.assignment.model.api.MovieDBApi
import com.tentwenty.assignment.repository.DefaultMainRepository
import com.tentwenty.assignment.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 *Created by Mustanser Iqbal
 */
private const val BASE_URL = "https://api.themoviedb.org/3/"
public const val API_KEY = "028a1f1f57c61cfd6d7038d5fe9013b3"

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule{

    @Singleton
    @Provides
    fun provideKanyeWestApi():MovieDBApi {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
            .create(MovieDBApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(movieDBApi: MovieDBApi) : MainRepository = DefaultMainRepository(movieDBApi)

}