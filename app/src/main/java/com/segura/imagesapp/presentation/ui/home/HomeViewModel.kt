package com.segura.imagesapp.presentation.ui.home

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.segura.imagesapp.data.paging.ImagePagingSource
import com.segura.imagesapp.domain.useCase.GetImagesUseCase
import com.segura.imagesapp.domain.useCase.InsertFavoriteImageUseCase
import com.segura.imagesapp.domain.model.ImageItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getImagesUseCase: GetImagesUseCase,
    private val insertFavoriteImageUseCase: InsertFavoriteImageUseCase
) : ViewModel() {


    fun insertFavoritePhoto(imageItem: ImageItem) {
        viewModelScope.launch {
            insertFavoriteImageUseCase.execute(imageItem)
        }
    }

    val images: Flow<PagingData<ImageItem>> = Pager(PagingConfig(pageSize = 10)) {
        ImagePagingSource(getImagesUseCase)
    }.flow
        .cachedIn(viewModelScope)

}