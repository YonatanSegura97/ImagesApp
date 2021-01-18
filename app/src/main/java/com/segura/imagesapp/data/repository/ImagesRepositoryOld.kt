package com.segura.imagesapp.data.repository

import androidx.lifecycle.LiveData
import com.segura.imagesapp.data.dataSource.ImagesRemoteDataSource
import com.segura.imagesapp.local.PhotosDao
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.model.SearchImagesResponse
import com.segura.imagesapp.model.User
import com.segura.imagesapp.model.photoDetail.PhotoDetailResponse
import com.segura.imagesapp.network.ResultWrapper
import com.segura.imagesapp.network.safeApiCall
import com.segura.imagesapp.utils.ConstantsUtils
import com.segura.imagesapp.utils.logDebug
import kotlinx.coroutines.Dispatchers

class ImagesRepositoryOld(
    private val imagesRemoteDataSource: ImagesRemoteDataSource,
    private val imagesLocalDataSource: PhotosDao
) {

    suspend fun getImageList(
        page: Int,
        perPage: Int
    ): ResultWrapper<List<ImageItem>> {
        return safeApiCall(Dispatchers.IO) {
            imagesRemoteDataSource.getImageList(
                clientId = ConstantsUtils.ACCESS_KEY,
                page = page,
                perPage = perPage
            )
        }
    }

    suspend fun searchImages(
        page: Int,
        perPage: Int,
        query: String
    ): ResultWrapper<SearchImagesResponse> {
        return safeApiCall(Dispatchers.IO) {
            imagesRemoteDataSource.searchImages(ConstantsUtils.ACCESS_KEY, page, perPage, query)
        }
    }

    suspend fun getPhotoDetails(photoId: String): ResultWrapper<PhotoDetailResponse> {
        return safeApiCall(Dispatchers.IO) {
            logDebug("PhotoDetailsId", photoId)
            imagesRemoteDataSource.getPhotoDetails(photoId, ConstantsUtils.ACCESS_KEY)
        }
    }

    suspend fun getUserDetails(userName: String): ResultWrapper<User> {
        return safeApiCall(Dispatchers.IO) {
            imagesRemoteDataSource.getUserDetails(userName, ConstantsUtils.ACCESS_KEY)
        }
    }

    //FOR LOCAL DATA ---
    val favoritesPhotos: LiveData<List<ImageItem>> = imagesLocalDataSource.getFavoritePhotos()

    suspend fun insertNewFavoritePhoto(imageItem: ImageItem) {
        safeApiCall(dispatcher = Dispatchers.Default) {
            imagesLocalDataSource.insertNewPhotoToFavorite(imageItem)
        }
    }

    suspend fun deleteFavoritePhoto(imageItem: ImageItem) {
        imagesLocalDataSource.deleteFavorite(imageItem.id)
    }


    suspend fun searchImages(query: String): List<ImageItem> {
        return imagesLocalDataSource.getPhotosWithFilter(query)
    }


}