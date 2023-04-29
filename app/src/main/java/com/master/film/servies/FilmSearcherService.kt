package com.master.film.servies

import com.master.film.callbacks.FilmInfoCallback
import com.master.film.callbacks.FilmSearchCallback

interface FilmSearcherService {
    fun getFilmById(id: String, filmInfoCallback: FilmInfoCallback)
    fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback)
}