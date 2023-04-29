package com.master.film.callbacks

import com.master.film.models.FilmSearch

interface FilmSearchCallback {
    fun process(filmSearch: FilmSearch)
}