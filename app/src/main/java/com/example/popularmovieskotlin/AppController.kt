package com.example.popularmovieskotlin

import android.app.Application
import androidx.multidex.MultiDexApplication
import timber.log.Timber


class AppController : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}