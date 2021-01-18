package com.segura.imagesapp.domain.contract

import com.segura.imagesapp.model.ImageItem

interface ImagesRepository {
   suspend  fun getImages(page: Int, perPage: Int): List<ImageItem>

}