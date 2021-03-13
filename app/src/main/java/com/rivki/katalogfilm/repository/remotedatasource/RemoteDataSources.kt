package com.rivki.katalogfilm.repository.remotedatasource

import com.rivki.katalogfilm.api.ApiService
import com.rivki.katalogfilm.base.BaseDataSource
import com.rivki.katalogfilm.base.ResourceState
import com.rivki.katalogfilm.base.ResponseWraper
import com.rivki.katalogfilm.model.response.BaseResponse
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

class RemoteDataSources(private val apiService: ApiService): BaseDataSource() {
    val LANGUAGE = "en-US"
    private suspend fun <T> getResultList(request: suspend () -> Response<BaseResponse<T>>): ResourceState<ResponseWraper<T>> {
        return try {
            val response = request()
            val body = response.body()
            if (response.isSuccessful.not() || body == null) return errorState(
                response.code(),
                response.message()
            )

            return ResourceState.Success(
                ResponseWraper(body.page, body.results, body.totalPages, null)
            )
        } catch (e: Exception) {
            errorState(msg = if (e is UnknownHostException) NO_INTERNET else e.localizedMessage.orEmpty())
        }
    }

    private suspend fun <T> getResult(request: suspend () -> Response<T>): ResourceState<ResponseWraper<T>> {
        return try {
            val response = request()
            val body = response.body()
            if (response.isSuccessful.not() || body == null) return errorState(
                response.code(),
                response.message()
            )

            return ResourceState.Success(
                ResponseWraper(null, body, null, null)
            )
        } catch (e: Exception) {
            errorState(msg = if (e is UnknownHostException) NO_INTERNET else e.localizedMessage.orEmpty())
        }
    }

    suspend fun getNowPlayingMovie(page: Int) = suspendDataResult { getResultList { apiService.getNowPlayingMovie(LANGUAGE, page) } }
    suspend fun getPopularMovie(page: Int) = suspendDataResult { getResultList { apiService.getPopularMovie(LANGUAGE, page) } }
    suspend fun getTopRatedMovie(page: Int) = suspendDataResult { getResultList { apiService.getTopRatedMovie(LANGUAGE, page) } }
    suspend fun getReviewMovie(id: Int) = suspendDataResult { getResultList { apiService.getMovieReview(id, LANGUAGE) } }
    suspend fun getDetalMovie(id: Int) = suspendDataResult { getResult { apiService.getDetailMovie(id, LANGUAGE)} }

    companion object {
        private const val NO_INTERNET = "Tidak ada koneksi internet"
    }
}