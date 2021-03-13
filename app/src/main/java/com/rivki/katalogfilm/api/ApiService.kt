package com.rivki.katalogfilm.api

import com.rivki.katalogfilm.model.response.BaseResponse
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.model.response.ReviewsResponse
import com.rivki.katalogfilm.model.response.TvResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ): SuccessCallbackList<List<MovieResponse>>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ): SuccessCallbackList<List<MovieResponse>>

    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("language") language: String,
        @Query("page") page: Int
    ): SuccessCallbackList<List<MovieResponse>>

    @GET("tv/airing_today")
    suspend fun getAiringTv(
        @Query("language") language: String,
        @Query("page") page: Int
    ): SuccessCallbackList<List<TvResponse>>

    @GET("movie/{id}")
    suspend fun getDetailMovie(
        @Path("id") id: Int,
        @Query("language") language: String
    ): SuccessCallback<MovieResponse>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReview(
        @Path("id") id: Int,
        @Query("language") language: String
    ): SuccessCallbackList<List<ReviewsResponse>>

    @GET("tv/{id}")
    suspend fun getDetailTv(
        @Path("id") id: Int,
        @Query("language") language: String
    ): SuccessCallback<TvResponse>
}

typealias SuccessCallbackList<T> = Response<BaseResponse<T>>
typealias SuccessCallback<T> = Response<T>