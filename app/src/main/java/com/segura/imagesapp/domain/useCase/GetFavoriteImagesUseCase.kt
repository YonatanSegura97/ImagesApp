package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.ImageItem
import kotlinx.coroutines.flow.Flow

class GetFavoriteImagesUseCase(private val imagesRepository: ImagesRepository) {
    fun execute(): Flow<List<ImageItem>> {
        return imagesRepository.getFavoriteImages()
    }
}