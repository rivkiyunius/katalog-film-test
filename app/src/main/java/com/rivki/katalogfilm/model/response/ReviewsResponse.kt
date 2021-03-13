package com.rivki.katalogfilm.model.response


import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("author")
    var author: String?,
    @SerializedName("author_details")
    var authorDetails: AuthorDetails?,
    @SerializedName("content")
    var content: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("id")
    var id: String?,
    @SerializedName("updated_at")
    var updatedAt: String?,
    @SerializedName("url")
    var url: String?
) {
    data class AuthorDetails(
        @SerializedName("avatar_path")
        var avatarPath: String?,
        @SerializedName("name")
        var name: String?,
        @SerializedName("rating")
        var rating: Any?,
        @SerializedName("username")
        var username: String?
    )
}