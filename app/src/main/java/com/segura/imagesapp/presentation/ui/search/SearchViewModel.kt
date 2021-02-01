package com.segura.imagesapp.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.segura.imagesapp.data.paging.SearchImagePagingSource
import com.segura.imagesapp.domain.useCase.InsertFavoriteImageUseCase
import com.segura.imagesapp.domain.useCase.SearchImagesUseCase
import com.segura.imagesapp.domain.model.ImageItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchImagesUseCase: SearchImagesUseCase,
    private val favoriteImageUseCase: InsertFavoriteImageUseCase
) : ViewModel() {
//    private val searchImageDataSource =
//        SearchImageDataSourceFactory(
//            repositoryOld = repositoryOld,
//            coroutineScope = viewModelScope,
//            query = ""
//        )
//    var imageList: LiveData<PagedList<ImageItem>>? = null
//    var networkState: LiveData<NetworkState>? = null
//    var initialNetworkState: LiveData<NetworkState>? = null
//
//    init {
//        initialNetworkState =
//            Transformations.switchMap(searchImageDataSource.source) { it.getInitialNetworkState() }
//        networkState =
//            Transformations.switchMap(searchImageDataSource.source) { it.getNetworkState() }
//        imageList = LivePagedListBuilder(searchImageDataSource, getPagingConfig()).build()
//    }


    fun searchImage(query: String): Flow<PagingData<ImageItem>> =
        Pager(PagingConfig(pageSize = 10)) {
            SearchImagePagingSource(useCase = searchImagesUseCase, query = query)
        }.flow.cachedIn(viewModelScope)


    fun insertFavoritePhoto(imageItem: ImageItem) {
        viewModelScope.launch {
            favoriteImageUseCase.execute(imageItem)
        }
    }

}