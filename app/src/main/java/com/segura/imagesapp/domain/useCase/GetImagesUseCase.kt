package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.ImageItem


class GetImagesUseCase(
    private val imagesRepository: ImagesRepository
)  {
  suspend  fun execute(page: Int, perPage: Int): List<ImageItem> {
        return imagesRepository.getImages(page, perPage)
    }


}