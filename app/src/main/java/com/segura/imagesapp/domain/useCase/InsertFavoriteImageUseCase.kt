package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.model.ImageItem

class InsertFavoriteImageUseCase(private val imagesRepository: ImagesRepository) {
    suspend fun execute(imageItem: ImageItem) {
        imagesRepository.insertImageToFavorite(imageItem)
    }
}