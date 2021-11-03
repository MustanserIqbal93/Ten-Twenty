package com.tentwenty.assignment.model

data class MoviesResponse (

    val page : Int,
    val results : ArrayList<Movie>,
    val backdrops : ArrayList<MovieImage>,
    val total_pages : Int,
    val total_results : Int
)