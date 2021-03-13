package com.rivki.katalogfilm.repository.localdatasource

import com.rivki.katalogfilm.base.BaseDataSource
import com.rivki.katalogfilm.base.ResourceState
import com.rivki.katalogfilm.base.ResponseWraper
import com.rivki.katalogfilm.model.db.MoviesDao
import com.rivki.katalogfilm.model.response.MovieResponse
import java.lang.Exception

class LocalDataSource(private val moviesDao: MoviesDao) : BaseDataSource() {
    private suspend fun <T> getResult(request: suspend () -> T): ResourceState<ResponseWraper<T>> {
        return try {
            val res = request.invoke()
            return ResourceState.Success(
                ResponseWraper(
                    page = null,
                    results = res,
                    totalPages = null,
                    error = null
                )
            )
        } catch (e: Exception) {
            errorState(msg = e.toString())
        }
    }

    suspend fun getAllFavorite() = suspendDataResult { getResult { moviesDao.getAll() } }
    suspend fun insertFavorite(movie: MovieResponse) =
        suspendDataResult { getResult { moviesDao.insertData(movie) } }

    suspend fun deleteFavorite(movie: MovieResponse) =
        suspendDataResult { getResult { moviesDao.deleteData(movie) } }

    suspend fun getFavoriteId(id: Int) = suspendDataResult { getResult { moviesDao.getFavorite(id) } }
}