package com.master.film.servies.impl

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.master.film.callbacks.FilmInfoCallback
import com.master.film.callbacks.FilmSearchCallback
import com.master.film.models.FilmInfo
import com.master.film.servies.FilmSearcherService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SqlLiteSearchService @Inject constructor(
    context: Context,
    private val filmSearchService: FilmSearcherService
) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), FilmSearcherService {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                FILM_ID_COL + " TEXT," +
                TITLE_COl + " TEXT," +
                FULL_TITLE_COL + " TEXT," +
                YEAR_COL + " TEXT," +
                IMAGE_COL + " TEXT," +
                PLOT_COL + " TEXT," +
                GENRES_COL + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addFilm(filmInfo: FilmInfo) {
        val values = ContentValues()

        values.put(FILM_ID_COL, filmInfo.id)
        values.put(TITLE_COl, filmInfo.title)
        values.put(FULL_TITLE_COL, filmInfo.fullTitle)
        values.put(YEAR_COL, filmInfo.year)
        values.put(IMAGE_COL, filmInfo.image)
        values.put(PLOT_COL, filmInfo.plot)
        values.put(GENRES_COL, filmInfo.genres)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    @SuppressLint("Range")
    override fun getFilmById(id: String, filmInfoCallback: FilmInfoCallback) {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE film_id = ?", arrayOf(id))

        if (cursor.count == 0) {
            filmSearchService.getFilmById(id, object : FilmInfoCallback {
                override fun process(filmInfo: FilmInfo) {
                    addFilm(filmInfo)
                    filmInfoCallback.process(filmInfo)
                }
            })
        } else {
            val filmInfo = FilmInfo()

            cursor!!.moveToFirst()
            filmInfo.id = cursor.getString(cursor.getColumnIndex(FILM_ID_COL))
            filmInfo.title = cursor.getString(cursor.getColumnIndex(TITLE_COl))
            filmInfo.fullTitle = cursor.getString(cursor.getColumnIndex(FULL_TITLE_COL))
            filmInfo.year = cursor.getString(cursor.getColumnIndex(YEAR_COL))
            filmInfo.image = cursor.getString(cursor.getColumnIndex(IMAGE_COL))
            filmInfo.plot = cursor.getString(cursor.getColumnIndex(PLOT_COL))
            filmInfo.genres = cursor.getString(cursor.getColumnIndex(GENRES_COL))

            filmInfoCallback.process(filmInfo)
        }
    }

    override fun searchFilms(searchString: String, filmSearchCallback: FilmSearchCallback) {
        filmSearchService.searchFilms(searchString, filmSearchCallback)
    }

    companion object {
        private const val DATABASE_NAME = "films"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "favorites"
        const val ID_COL = "id"
        const val FILM_ID_COL = "film_id"
        const val TITLE_COl = "title"
        const val FULL_TITLE_COL = "full_title"
        const val YEAR_COL = "year"
        const val IMAGE_COL = "image"
        const val PLOT_COL = "plot"
        const val GENRES_COL = "genres"
    }
}