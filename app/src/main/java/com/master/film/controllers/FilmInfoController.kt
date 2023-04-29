package com.master.film.controllers

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.master.film.callbacks.WatchedCallback
import com.master.film.callbacks.FilmInfoCallback
import com.master.film.callbacks.FilmSearchCallback
import com.master.film.models.WatchedFilms
import com.master.film.models.FilmInfo
import com.master.film.servies.FilmSearcherService
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class FilmInfoController @Inject constructor(@Named("filmFireBaseSearchService") private val filmSearchService: FilmSearcherService) {

    fun getById(id: String, filmInfoCallback: FilmInfoCallback) {
        filmSearchService.getFilmById(id, filmInfoCallback)
    }

    fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback) {
        filmSearchService.searchFilms(searchString, filmSearchCallback)
    }

    fun addToWatched(filmInfo: FilmInfo) {
        val db = FirebaseDatabase.getInstance()
        val filmReference = db.getReference("watched")
        var added = false

        filmReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val oldWatched = dataSnapshot.getValue(WatchedFilms::class.java)
                if (added) {
                    return
                }
                added = if (oldWatched == null) {
                    val watched = WatchedFilms()
                    watched.films.add(filmInfo)
                    filmReference.setValue(watched)
                    true
                } else {
                    oldWatched.films.add(filmInfo)
                    filmReference.setValue(oldWatched)
                    true
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }

    fun getWatched(favoritesCallback: WatchedCallback) {
        val db = FirebaseDatabase.getInstance()
        val favoritesReference = db.getReference("watched")

        favoritesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val watched = dataSnapshot.getValue(WatchedFilms::class.java)
                if (watched == null) {
                    favoritesCallback.process(WatchedFilms())
                } else {
                    favoritesCallback.process(watched)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }
}