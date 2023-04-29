package com.master.film.servies

import com.master.film.models.FilmInfo
import com.master.film.models.FilmSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface FilmService {
    @GET("/en/API/SearchMovie/k_clo44w1m/{search}")
    fun getFilms(@Path(value="search", encoded=false) search: String): Call<FilmSearch?>?

    @GET("/en/API/Title/k_clo44w1m/{filmId}/Trailer")
    fun getFilmInfo(@Path(value="filmId", encoded=false) filmId: String): Call<FilmInfo?>?
}