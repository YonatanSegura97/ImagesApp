package com.segura.imagesapp.presentation.ui.home

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.segura.imagesapp.data.paging.ImagePagingSource
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.domain.useCase.GetImagesUseCase
import com.segura.imagesapp.model.ImageItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val getImagesUseCase: GetImagesUseCase) : ViewModel() {

//    private val imagesDataSource = ImageListDataSourceFactory(repository, viewModelScope)
//    var imageList: LiveData<PagedList<ImageItem>>? = null
//    var networkState: LiveData<NetworkState>? = null
//    var initialNetworkState: LiveData<NetworkState>? = null
//
//    init {
//        initialNetworkState =
//            Transformations.switchMap(imagesDataSource.source) { it.getInitialNetworkState() }
//        networkState = Transformations.switchMap(imagesDataSource.source) { it.getNetworkState() }
//        imageList = LivePagedListBuilder(imagesDataSource, getPagingConfig()).build()
//
//    }
//
//
//    fun insertFavoritePhoto(imageItem: ImageItem) {
//        viewModelScope.launch {
//            repository.insertNewFavoritePhoto(imageItem)
//        }
//    }

    private val _imageList = MutableLiveData<Resource<List<ImageItem>>>()
    val imageList: LiveData<Resource<List<ImageItem>>> get() = _imageList

    val images: Flow<PagingData<ImageItem>> = Pager(PagingConfig(pageSize = 10)) {
        ImagePagingSource(getImagesUseCase)
    }.flow
        .cachedIn(viewModelScope)

}