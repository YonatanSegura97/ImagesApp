package com.segura.imagesapp.data.dataSource

import com.segura.imagesapp.domain.model.ImageItem
import com.segura.imagesapp.domain.model.SearchImagesResponse
import com.segura.imagesapp.domain.model.User
import com.segura.imagesapp.domain.model.photoDetail.PhotoDetailResponse
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
    suspend fun getImageDetail(
        @Path("id") photoId: String,
        @Query("client_id") clientId: String,
    ): PhotoDetailResponse

    @GET("users/{username}")
    suspend fun getUserDetails(
        @Path("username") username: String,
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