 package com.rivki.katalogfilm.repository

import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.repository.localdatasource.LocalDataSource
import com.rivki.katalogfilm.repository.remotedatasource.RemoteDataSources

class DataRepository(private val remoteDataSources: RemoteDataSources, private val localDataSource: LocalDataSource) {
    suspend fun getNowPlayingMovie(page: Int) = remoteDataSources.getNowPlayingMovie(page)
    suspend fun getPopularMovie(page: Int) = remoteDataSources.getPopularMovie(page)
    suspend fun getTopRatedMovie(page: Int) = remoteDataSources.getTopRatedMovie(page)
    suspend fun getReviewMovie(id: Int) = remoteDataSources.getReviewMovie(id)
    suspend fun getDetailMovie(id: Int) = remoteDataSources.getDetalMovie(id)
    suspend fun getAllFavorite() = localDataSource.getAllFavorite()
    suspend fun addToFavorite(movie: MovieResponse) = localDataSource.insertFavorite(movie)
    suspend fun deleteFavorite(movie: MovieResponse) = localDataSource.deleteFavorite(movie)
    suspend fun getFavoriteStatus(id: Int) = localDataSource.getFavoriteId(id)
}