package com.rivki.katalogfilm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rivki.katalogfilm.base.BaseViewModel
import com.rivki.katalogfilm.base.ResourceState
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.model.response.ReviewsResponse
import com.rivki.katalogfilm.repository.DataRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: DataRepository) : BaseViewModel() {
    private val _movieDetail = MutableLiveData<MovieResponse>()
    private val _listReview = MutableLiveData<List<ReviewsResponse>>()
    private val _favoriteStatus = MutableLiveData<Boolean>()
    val detailMovie: LiveData<MovieResponse> get() = _movieDetail
    val listReview: LiveData<List<ReviewsResponse>> get() = _listReview
    val favoriteStatus: LiveData<Boolean> get() = _favoriteStatus

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            when(val request = repository.getDetailMovie(movieId)){
                is ResourceState.Success -> {
                    _movieDetail.postValue(request.result.results!!)
                }
                is ResourceState.Error -> {
                    errorResponse.postValue("Terdapat kesalahan pada server")
                }
            }
        }
    }

    fun getFavoriteStatus(movieId: Int){
        viewModelScope.launch {
            when(val request = repository.getFavoriteStatus(movieId)){
                is ResourceState.Success -> {
                    val status = request.result.results ?: false
                    _favoriteStatus.postValue(status)
                }
                is ResourceState.Error -> {
                    errorResponse.postValue("Terdapat kesalahan pada server")
                }
            }
        }
    }

    fun getReviewList(movieId: Int){
        viewModelScope.launch {
            when(val request = repository.getReviewMovie(movieId)){
                is ResourceState.Success -> {
                    _listReview.postValue(request.result.results!!)
                }
                is ResourceState.Error -> {
                    errorResponse.postValue("Terdapat kesalahan pada server")
                }
            }
        }
    }

    fun addFavorite(movie: MovieResponse){
        movie.isFavorite = true
        viewModelScope.launch {
            when(repository.addToFavorite(movie)){
                is ResourceState.Success -> {}
                is ResourceState.Error -> {
                    errorResponse.postValue("Terdapat kesalahan pada server")
                }
            }
        }
    }

    fun deleteFavorite(movieResponse: MovieResponse){
        viewModelScope.launch {
            when(repository.deleteFavorite(movieResponse)){
                is ResourceState.Success -> {}
                is ResourceState.Error -> {
                    errorResponse.postValue("Terdapat kesalahan pada server")
                }
            }
        }
    }
}