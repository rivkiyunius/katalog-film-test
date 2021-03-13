package com.rivki.katalogfilm.base

sealed class ResourceState<out T>{
    data class Success<out T>(val result: T): ResourceState<T>()
    data class Error<T>(val error: T) : ResourceState<T>()
}

data class ErrorResponse(val code: Int? = 0, val msg: String? = "", val error: String? = "")
data class ResponseWraper<out T>(
    val page: Int?,
    val results: T?,
    val totalPages: Int?,
    val error: ErrorResponse?
)