package com.rivki.katalogfilm.ui

import com.rivki.katalogfilm.base.ResourceState
import com.rivki.katalogfilm.base.ResponseWraper
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.model.response.TvResponse

object FakeRepository {
    fun getDataMovies(): ResourceState<ResponseWraper<List<MovieResponse>>> {
        return ResourceState.Success(
            ResponseWraper(1, dataMovie(), 1, null)
        )
    }

    fun getFavoriteStatus(status: Boolean): ResourceState<ResponseWraper<Boolean>> {
        return ResourceState.Success(
            ResponseWraper(1, status, 1, null)
        )
    }

    fun getDetailMovie(): ResourceState<ResponseWraper<MovieResponse>> {
        return ResourceState.Success(
            ResponseWraper(
                null,
                MovieResponse("", 0, "", "", "", 0.0, "", "", "", 0.0, 0),
                null,
                null
            )
        )
    }

    private fun dataMovie(): List<MovieResponse> {
        val movies = ArrayList<MovieResponse>()
        movies.add(MovieResponse("", 0, "", "", "", 0.0, "", "", "", 0.0, 0))
        movies.add(MovieResponse("", 2, "", "", "", 0.0, "", "", "", 0.0, 0))
        movies.add(MovieResponse("", 3, "", "", "", 0.0, "", "", "", 0.0, 0))
        movies.add(MovieResponse("", 4, "", "", "", 0.0, "", "", "", 0.0, 0))
        movies.add(MovieResponse("", 5, "", "", "", 0.0, "", "", "", 0.0, 0))
        return movies
    }
}