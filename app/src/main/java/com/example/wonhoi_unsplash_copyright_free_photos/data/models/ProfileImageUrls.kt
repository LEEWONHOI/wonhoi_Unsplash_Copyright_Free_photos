package com.example.wonhoi_unsplash_copyright_free_photos.data.models

import com.google.gson.annotations.SerializedName

data class ProfileImageUrls(
    @SerializedName("large")
    val large : String? = null,
    @SerializedName("medium")
    val medium : String? = null,
    @SerializedName("small")
    val small : String? = null
)