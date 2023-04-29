package com.master.film.config

import android.content.Context
import android.app.Application
import com.master.film.components.ActivityComponent
import com.master.film.components.DaggerActivityComponent

class Application : Application() {
    val activityComponent: ActivityComponent = DaggerActivityComponent.create()

    init {
        instance = this
    }

    companion object {
        lateinit var instance: com.master.film.config.Application

        fun getContext(): Context {
            return instance
        }
    }
}