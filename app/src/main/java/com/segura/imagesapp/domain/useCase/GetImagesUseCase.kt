package com.segura.imagesapp.domain.useCase

import androidx.paging.PagingSource
import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.model.ImageItem
import kotlinx.coroutines.flow.Flow


class GetImagesUseCase(
    private val imagesRepository: ImagesRepository
)  {
  suspend  fun execute(page: Int, perPage: Int): List<ImageItem> {
        return imagesRepository.getImages(page, perPage)
    }


}