package com.master.film.components

import com.master.film.activities.FavouriteFilmActivity
import com.master.film.activities.FilmInfoActivity
import com.master.film.activities.LoginActivity
import com.master.film.activities.SearchFilmActivity
import com.master.film.modules.SearchModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SearchModule::class])
interface ActivityComponent {
    fun injectLoginActivity(loginActivity: LoginActivity)
    fun injectFilmListActivity(favoritesActivity: FavouriteFilmActivity)
    fun injectFilmSearchActivity(filmSearchActivity: SearchFilmActivity)
    fun injectFilmInfoActivity(filmInfoActivity: FilmInfoActivity)
}