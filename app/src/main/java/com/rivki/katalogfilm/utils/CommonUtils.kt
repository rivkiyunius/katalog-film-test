package com.rivki.katalogfilm.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rivki.katalogfilm.BuildConfig

object CommonUtils {
    fun ImageView.showImage(url: String) {
        Glide.with(this).load(BuildConfig.BASE_URL_IMAGE + url).into(this)
    }

    fun ImageView.showImageReview(url: String) {
        val urlSubstring = url.substring(1)
        Glide.with(this).load(urlSubstring).into(this)
    }

    fun String.substringText(): String {
        return if (this.length > 50) "${this.substring(0, 50)} ..." else this
    }
}