package com.master.film.callbacks

import com.master.film.models.WatchedFilms

interface WatchedCallback {
    fun process(favorites: WatchedFilms)
}