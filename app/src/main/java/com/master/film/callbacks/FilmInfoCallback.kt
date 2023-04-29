package com.master.film.callbacks

import com.master.film.models.FilmInfo

interface FilmInfoCallback {
    fun process(filmInfo: FilmInfo)
}