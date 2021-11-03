package com.tentwenty.assignment.model.movieDetail

data class MovieDetailResponse (

    val genres : ArrayList<Genre>,
    val original_title : String,
    val overview : String,
    val poster_path : String,
    val backdrop_path : String,
    val release_date : String,
    var commaSeparatedGenre : String,
    val vote_average : Float,
)

