package com.master.film.servies.impl

import com.master.film.callbacks.FilmInfoCallback
import com.master.film.callbacks.FilmSearchCallback
import com.master.film.models.FilmInfo
import com.master.film.models.FilmSearch
import com.master.film.servies.FilmSearcherService
import com.master.film.servies.FilmService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmApiSearchService @Inject constructor(private val filmService: FilmService):
    FilmSearcherService {

    override fun getFilmById(id: String, filmInfoCallback: FilmInfoCallback) {
        val call: Call<FilmInfo?>? = filmService.getFilmInfo(id)

        call?.enqueue(object : Callback<FilmInfo?> {
            override fun onResponse(
                call: Call<FilmInfo?>,
                response: Response<FilmInfo?>,
            ) {
                filmInfoCallback.process(response.body()!!)
            }

            override fun onFailure(call: Call<FilmInfo?>, t: Throwable) {
                println("Error: $t")
            }
        })
    }

    override fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback) {
        val call: Call<FilmSearch?>? = filmService.getFilms(searchString)

        call?.enqueue(object : Callback<FilmSearch?> {
            override fun onResponse(
                call: Call<FilmSearch?>,
                response: Response<FilmSearch?>,
            ) {
                filmSearchCallback.process(response.body()!!)
            }

            override fun onFailure(call: Call<FilmSearch?>, t: Throwable) {
                println("Error: $t")
            }
        })
    }
}