package com.master.film.callbacks

import com.master.film.models.Favorites

interface FavouritesCallback {
    fun process(favorites: Favorites)
}