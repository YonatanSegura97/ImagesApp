package com.segura.imagesapp.data.repository


import com.segura.imagesapp.BuildConfig
import com.segura.imagesapp.data.dataSource.ImagesRemoteDataSource
import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.data.dataSource.ImagesDao
import com.segura.imagesapp.domain.model.ImageItem
import com.segura.imagesapp.domain.model.SearchImagesResponse
import com.segura.imagesapp.domain.model.User
import com.segura.imagesapp.domain.model.photoDetail.PhotoDetailResponse
import com.segura.imagesapp.utils.ConstantsUtils
import com.segura.imagesapp.utils.DefaultDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class ImageRepositoryImpl(
    private val imagesRemoteDataSource: ImagesRemoteDataSource,
    private val dispatcherProvider: DefaultDispatcherProvider,
    private val imagesLocalDataSource: ImagesDao
) : ImagesRepository {
    override suspend fun getImages(page: Int, perPage: Int): List<ImageItem> {
        return imagesRemoteDataSource.getImageList(
            clientId = BuildConfig.ACCESS_KEY,
            page,
            perPage
        )
    }

    override fun getImageDetail(imageId: String): Flow<Resource<PhotoDetailResponse>> {

        return flow {
            emit(Resource.loading(null))
            val imageDetailResponse = imagesRemoteDataSource.getImageDetail(
                photoId = imageId,
                clientId = BuildConfig.ACCESS_KEY
            )
            emit(Resource.success(imageDetailResponse))

        }.catch {
            emit(Resource.error(it.toString(), null))
        }.flowOn(dispatcherProvider.io())
    }

    override fun getFavoriteImages(): Flow<List<ImageItem>> {
        return imagesLocalDataSource.getFavoriteImages().flowOn(dispatcherProvider.io())
    }

    override suspend fun insertImageToFavorite(imageItem: ImageItem) {
        withContext(dispatcherProvider.io()) {
            imagesLocalDataSource.insertNewPhotoToFavorite(imageItem)
        }
    }

    override suspend fun deleteImageToFavorite(imageItem: ImageItem) {
        withContext(dispatcherProvider.io()) {
            imagesLocalDataSource.deleteFavorite(imageItem.id)
        }
    }

    override suspend fun searchImages(query: String): List<ImageItem> {
        return withContext(dispatcherProvider.io()) {
            imagesLocalDataSource.searchImages(query)
        }
    }

    override fun getUserDetail(userName: String): Flow<Resource<User>> {
        return flow {
            emit(Resource.loading(null))
            val response = imagesRemoteDataSource.getUserDetails(
                username = userName,
                clientId = BuildConfig.ACCESS_KEY
            )
            emit(Resource.success(response))
        }.catch {
            emit(Resource.error(it.toString(), null))
        }
    }

    override suspend fun searchImagesByQuery(
        query: String,
        page: Int,
        perPage: Int
    ): SearchImagesResponse {
        return withContext(dispatcherProvider.io()) {
            imagesRemoteDataSource.searchImages(BuildConfig.ACCESS_KEY, page, perPage, query)
        }
    }


}