package com.rivki.katalogfilm.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    protected val errorResponse = MutableLiveData<String>()
    protected val isLoading = MutableLiveData<Boolean>()
    protected val isFetching = MutableLiveData<Boolean>()
    protected val isEmptyData = MutableLiveData<Boolean>()

    fun observeError(): LiveData<String> = errorResponse
    fun observeLoading(): LiveData<Boolean> = isLoading
    fun observeFetching(): LiveData<Boolean> = isFetching
    fun observeEmptyData(): LiveData<Boolean> = isEmptyData
}