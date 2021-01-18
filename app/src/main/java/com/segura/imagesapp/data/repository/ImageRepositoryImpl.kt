package com.segura.imagesapp.data.repository

import com.segura.imagesapp.data.dataSource.ImagesLocalDataSource
import com.segura.imagesapp.data.dataSource.ImagesRemoteDataSource
import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.utils.ConstantsUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ImageRepositoryImpl(
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) : ImagesRepository {
    override suspend fun getImages(page: Int, perPage: Int): List<ImageItem> {
        return imagesRemoteDataSource.getImageList(
            clientId = ConstantsUtils.ACCESS_KEY,
            page,
            perPage
        )
    }


}