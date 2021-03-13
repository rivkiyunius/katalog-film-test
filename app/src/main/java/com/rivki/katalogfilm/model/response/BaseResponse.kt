package com.rivki.katalogfilm.model.response

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: T,
    @SerializedName("total_pages")
    val totalPages: Int
)
