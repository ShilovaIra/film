package com.master.film.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.master.film.R
import com.master.film.callbacks.FilmSearchCallback
import com.master.film.config.Application
import com.master.film.controllers.FilmInfoController
import com.master.film.models.Film
import com.master.film.models.FilmSearch
import javax.inject.Inject

class SearchFilmActivity : AppCompatActivity() {

    @Inject
    lateinit var filmInfoController: FilmInfoController

    private var listView: ListView? = null
    private var searchButton: Button? = null
    private var search: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).activityComponent.injectFilmSearchActivity(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_film_list)

        listView = findViewById(R.id.filmListView)
        search = findViewById(R.id.searchFilms)
        searchButton = findViewById(R.id.searchButton)

        val context = this

        searchButton!!.setOnClickListener {
            val searchString = search!!.text.toString()

            filmInfoController.searchFilms(searchString, object : FilmSearchCallback {
                override fun process(filmSearch: FilmSearch) {
                    drawList(filmSearch.results!!, context)
                }
            })
        }
    }

    private fun drawList(films: List<Film>, context: SearchFilmActivity) {
        listView!!.setOnItemClickListener { _, _, position, _ ->
            val selectedFilm = films[position]
            val detailIntent = FilmInfoActivity.newIntent(context, selectedFilm.id!!)
            startActivity(detailIntent)
        }

        val filmInfoList = arrayOfNulls<String>(films.size)
        for (i in films.indices) {
            val film = films[i]
            filmInfoList[i] = film.title + " " + film.description
        }

        val adapter = ArrayAdapter(
            context,
            android.R.layout.simple_list_item_1,
            filmInfoList
        )
        listView!!.adapter = adapter
    }
}