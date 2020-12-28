package com.segura.imagesapp.model.photoDetail


import com.google.gson.annotations.SerializedName
import com.segura.imagesapp.model.Urls

data class PreviewPhoto(
    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("urls")
    val urls: Urls
)