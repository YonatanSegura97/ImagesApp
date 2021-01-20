package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository

class SearchImagesUserCase(private val repository: ImagesRepository) {
    suspend fun execute(query: String) {

    }
}