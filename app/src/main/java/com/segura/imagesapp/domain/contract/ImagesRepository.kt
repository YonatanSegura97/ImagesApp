package com.segura.imagesapp.domain.contract

import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.domain.model.ImageItem
import com.segura.imagesapp.domain.model.SearchImagesResponse
import com.segura.imagesapp.domain.model.User
import com.segura.imagesapp.domain.model.photoDetail.PhotoDetailResponse
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {
    suspend fun getImages(page: Int, perPage: Int): List<ImageItem>
    fun getImageDetail(photoId: String): Flow<Resource<PhotoDetailResponse>>
    fun getFavoriteImages(): Flow<List<ImageItem>>
    suspend fun insertImageToFavorite(imageItem: ImageItem)
    suspend fun deleteImageToFavorite(imageItem: ImageItem)
    suspend fun searchImages(query: String): List<ImageItem>
    fun getUserDetail(userName: String): Flow<Resource<User>>
    suspend fun searchImagesByQuery(query: String, page: Int, perPage: Int): SearchImagesResponse

}