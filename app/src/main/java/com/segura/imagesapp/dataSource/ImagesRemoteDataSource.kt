package com.segura.imagesapp.dataSource

import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.model.SearchImagesResponse
import com.segura.imagesapp.model.User
import com.segura.imagesapp.model.photoDetail.PhotoDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImagesRemoteDataSource {

    @GET("photos")
    suspend fun getImageList(
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<ImageItem>


    @GET("photos/{id}")
    suspend fun getPhotoDetails(
        @Path("id") photoId: String,
        @Query("client_id") clientId: String,
    ): PhotoDetailResponse

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") photoId: String,
        @Query("client_id") clientId: String,
    ): User

    @GET("search/photos")
    suspend fun searchImages(
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String
    ): SearchImagesResponse

}