package com.segura.imagesapp.domain.useCase

import com.segura.imagesapp.domain.contract.ImagesRepository
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.domain.model.User
import kotlinx.coroutines.flow.Flow

class GetUserDetailUseCase(private val repository: ImagesRepository) {
    fun execute(username: String): Flow<Resource<User>> {
        return repository.getUserDetail(username)
    }
}