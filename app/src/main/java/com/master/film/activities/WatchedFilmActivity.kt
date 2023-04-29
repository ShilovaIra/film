package com.master.film.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.master.film.R
import com.master.film.callbacks.WatchedCallback
import com.master.film.config.Application
import com.master.film.controllers.FilmInfoController
import com.master.film.models.WatchedFilms
import com.master.film.models.FilmInfo
import javax.inject.Inject

class WatchedFilmActivity : AppCompatActivity() {
    @Inject
    lateinit var filmController: FilmInfoController

    private var listView: ListView? = null
    private var addFilmButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).activityComponent.injectFilmListActivity(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        listView = findViewById(R.id.filmListView)
        addFilmButton = findViewById(R.id.addFilmButton)

        val context = this

        filmController.getWatched(object : WatchedCallback {
            override fun process(favorites: WatchedFilms) {
                val films = favorites.films
                drawList(films, context)
            }
        })

        addFilmButton!!.setOnClickListener {
            val intent = Intent(this, SearchFilmActivity::class.java)
            startActivity(intent)
        }
    }

    private fun drawList(films: List<FilmInfo>, context: Context) {
        listView!!.setOnItemClickListener { _, _, position, _ ->
            val selectedFilm = films[position]
            val detailIntent = FilmInfoActivity.newIntent(context, selectedFilm.id!!)
            startActivity(detailIntent)
        }

        val filmInfoList = arrayOfNulls<String>(films.size)
        for (i in films.indices) {
            val film = films[i]
            filmInfoList[i] = film.title + " " + film.year
        }

        val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, filmInfoList)
        listView!!.adapter = adapter
    }
}