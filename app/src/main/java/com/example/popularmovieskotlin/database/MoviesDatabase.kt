package com.example.popularmovieskotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntityMovie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}

private lateinit var INSTANCE: MoviesDatabase

fun getDatabase(context: Context): MoviesDatabase {
    synchronized(MoviesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext, MoviesDatabase::class.java, "movies").build()
        }
    }
    return INSTANCE
}