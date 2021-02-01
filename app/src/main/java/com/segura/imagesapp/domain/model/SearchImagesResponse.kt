package com.segura.imagesapp.domain.model

import com.google.gson.annotations.SerializedName

data class SearchImagesResponse(
    @SerializedName("total")
    val total: Int,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("results")
    val results: List<ImageItem>,
)
