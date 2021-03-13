package com.rivki.katalogfilm.repository.localdatasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivki.katalogfilm.model.db.MoviesDao
import com.rivki.katalogfilm.model.response.MovieResponse

@Database(entities = [MovieResponse::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}