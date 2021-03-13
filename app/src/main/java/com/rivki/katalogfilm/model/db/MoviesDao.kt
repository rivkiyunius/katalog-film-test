package com.rivki.katalogfilm.model.db

import androidx.room.*
import com.rivki.katalogfilm.model.response.MovieResponse

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    suspend fun getAll(): List<MovieResponse>

    @Query("SELECT isFavorite FROM movies WHERE id = :id")
    suspend fun getFavorite(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(vararg movie: MovieResponse)

    @Delete
    suspend fun deleteData(vararg movie: MovieResponse)
}