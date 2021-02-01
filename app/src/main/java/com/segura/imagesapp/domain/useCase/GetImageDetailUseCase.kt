package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.domain.model.photoDetail.PhotoDetailResponse
import kotlinx.coroutines.flow.Flow

class GetImageDetailUseCase(
    private val imagesRepository: ImagesRepository
) {
    fun execute(imageId:String):Flow<Resource<PhotoDetailResponse>>{
        return imagesRepository.getImageDetail(imageId)
    }
}