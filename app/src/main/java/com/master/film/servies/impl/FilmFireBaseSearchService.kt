package com.master.film.servies.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.master.film.callbacks.FilmInfoCallback
import com.master.film.callbacks.FilmSearchCallback
import com.master.film.models.FilmInfo
import com.master.film.servies.FilmSearcherService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmFireBaseSearchService @Inject constructor(private val filmSearchService: FilmSearcherService):
    FilmSearcherService {

    override fun getFilmById(id: String, filmInfoCallback: FilmInfoCallback) {
        val db = FirebaseDatabase.getInstance()
        val filmReference = db.getReference(id)

        filmReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val filmInfo = dataSnapshot.getValue(FilmInfo::class.java)
                if (filmInfo == null) {
                    filmSearchService.getFilmById(id, object : FilmInfoCallback {
                        override fun process(filmInfo: FilmInfo) {
                            filmReference.setValue(filmInfo)
                        }
                    })
                } else {
                    filmInfoCallback.process(filmInfo)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error: $databaseError")
            }
        })
    }

    override fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback) {
        filmSearchService.searchFilms(searchString, filmSearchCallback)
    }
}