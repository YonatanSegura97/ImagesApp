package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.model.SearchImagesResponse

class SearchImagesUseCase(private val repository: ImagesRepository) {
    suspend fun execute(query: String, page: Int, perPage: Int): SearchImagesResponse {
        return repository.searchImagesByQuery(query, page, perPage)
    }
}