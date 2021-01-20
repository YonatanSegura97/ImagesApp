package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.model.ImageItem

class SearchFavoriteImagesUseCase(private val repository: ImagesRepository) {

    suspend fun execute(query: String): List<ImageItem> {
        return repository.searchImages(query)
    }
}