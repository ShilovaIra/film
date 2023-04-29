package com.master.film.modules

import com.master.film.config.Application
import com.master.film.config.RetrofitClientInstance
import com.master.film.servies.FilmSearcherService
import com.master.film.servies.FilmService
import com.master.film.servies.impl.FilmApiSearchService
import com.master.film.servies.impl.FilmFireBaseSearchService
import com.master.film.servies.impl.SqlLiteSearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Named
import javax.inject.Singleton

@Module
@DisableInstallInCheck
class SearchModule {
    @Singleton
    @Provides
    @Named("filmApiSearchService")
    fun filmApiSearchService(): FilmSearcherService {
        return FilmApiSearchService(filmService())
    }

    @Singleton
    @Provides
    @Named("filmFireBaseSearchService")
    fun filmFireBaseSearchService(): FilmSearcherService {
        return FilmFireBaseSearchService(sqlLiteSearchService())
    }

    @Singleton
    @Provides
    @Named("sqlLiteSearchService")
    fun sqlLiteSearchService(): FilmSearcherService {
        return SqlLiteSearchService(Application.getContext(), filmApiSearchService())
    }

    @Singleton
    @Provides
    fun filmService(): FilmService {
        return RetrofitClientInstance.retrofitInstance!!.create(
            FilmService::class.java
        )
    }
}