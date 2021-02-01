package com.segura.imagesapp.domain.model.photoDetail


import com.google.gson.annotations.SerializedName
import com.segura.imagesapp.domain.model.Urls
import com.segura.imagesapp.domain.model.User

data class PhotoDetailResponse(
    @SerializedName("alt_description")
    val altDescription: String,
    @SerializedName("blur_hash")
    val blurHash: String,
    @SerializedName("color")
    val color: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: Any,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("exif")
    val exif: Exif,
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("liked_by_user")
    val likedByUser: Boolean,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("promoted_at")
    val promotedAt: Any,
    @SerializedName("related_collections")
    val relatedCollections: RelatedCollections,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("views")
    val views: Int,
    @SerializedName("width")
    val width: Int,
    @SerializedName("user")
    val user: User,
    @SerializedName("urls")
    val urls: Urls
)