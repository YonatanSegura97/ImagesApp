package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.model.ImageItem

class DeleteFavoriteImageUseCase(private val repository: ImagesRepository) {
    suspend fun execute(imageItem: ImageItem) {

    }
}