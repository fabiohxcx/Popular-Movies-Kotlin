package com.example.popularmovieskotlin.detailmovie

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.jetbrains.anko.toast

class DetailObserver(lifecycle: Lifecycle, context: Context?) : LifecycleObserver {

    var context = context

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStartFragment() {
        context?.toast("detail fragment start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStopFragment() {
        context?.toast("detail fragment stop")
    }

}