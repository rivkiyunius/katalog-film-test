package com.rivki.katalogfilm.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rivki.katalogfilm.base.BaseViewModel
import com.rivki.katalogfilm.base.ResourceState
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.repository.DataRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: DataRepository) : BaseViewModel() {
    private val _nowPlayingMovie = MutableLiveData<List<MovieResponse>>()
    private val _popularMovie = MutableLiveData<List<MovieResponse>>()
    private val _topRatedMovie = MutableLiveData<List<MovieResponse>>()
    val nowPlayingMovie: LiveData<List<MovieResponse>> get() =  _nowPlayingMovie
    val popularMovie: LiveData<List<MovieResponse>> get() =  _popularMovie
    val topRatedMovie: LiveData<List<MovieResponse>> get() = _topRatedMovie

    fun getMovieList(){
        isLoading.postValue(true)
        viewModelScope.launch {
            when(val request = repository.getNowPlayingMovie(1)){
                is ResourceState.Success -> {
                    _nowPlayingMovie.postValue(request.result.results!!)
                    isLoading.postValue(false)
                }
                is ResourceState.Error -> {
                    errorResponse.postValue(request.error.toString())
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun getPopularMovie(){
        isLoading.postValue(true)
        viewModelScope.launch {
            when(val request = repository.getPopularMovie(1)){
                is ResourceState.Success -> {
                    _popularMovie.postValue(request.result.results!!)
                    isLoading.postValue(false)
                }
                is ResourceState.Error -> {
                    errorResponse.postValue(request.error.toString())
                    isLoading.postValue(false)
                }
            }
        }
    }

    fun getTopRatedMovie(){
        isLoading.postValue(true)
        viewModelScope.launch {
            when(val request = repository.getTopRatedMovie(1)){
                is ResourceState.Success -> {
                    _topRatedMovie.postValue(request.result.results!!)
                    isLoading.postValue(false)
                }
                is ResourceState.Error -> {
                    errorResponse.postValue(request.error.toString())
                    isLoading.postValue(false)
                }
            }
        }
    }
}