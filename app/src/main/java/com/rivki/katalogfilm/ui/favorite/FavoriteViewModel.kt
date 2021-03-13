package com.rivki.katalogfilm.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rivki.katalogfilm.base.BaseViewModel
import com.rivki.katalogfilm.base.ResourceState
import com.rivki.katalogfilm.model.response.MovieResponse
import com.rivki.katalogfilm.repository.DataRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: DataRepository): BaseViewModel() {
    private val _favoriteList = MutableLiveData<List<MovieResponse>>()
    val favoriteList: LiveData<List<MovieResponse>> get() = _favoriteList

    fun getFavoriteList(){
        isLoading.postValue(true)
        viewModelScope.launch {
            when(val request = repository.getAllFavorite()){
                is ResourceState.Success -> {
                    _favoriteList.postValue(request.result.results!!)
                    isLoading.postValue(false)
                }
                is ResourceState.Error -> {

                }
            }
        }
    }
}