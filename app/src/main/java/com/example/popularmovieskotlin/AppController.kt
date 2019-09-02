package com.example.popularmovieskotlin

import android.app.Application
import timber.log.Timber


class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}