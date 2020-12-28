package com.segura.imagesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.segura.imagesapp.dataSource.ImagesRepository
import com.segura.imagesapp.di.repositoryModule
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.network.NetworkState
import com.segura.imagesapp.network.pagging.photoList.ImageListDataSourceFactory
import com.segura.imagesapp.utils.getPagingConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ImagesRepository) : ViewModel() {

    private val imagesDataSource = ImageListDataSourceFactory(repository, viewModelScope)
    var imageList: LiveData<PagedList<ImageItem>>? = null
    var networkState: LiveData<NetworkState>? = null
    var initialNetworkState: LiveData<NetworkState>? = null

    init {
        initialNetworkState =
            Transformations.switchMap(imagesDataSource.source) { it.getInitialNetworkState() }
        networkState = Transformations.switchMap(imagesDataSource.source) { it.getNetworkState() }
        imageList = LivePagedListBuilder(imagesDataSource, getPagingConfig()).build()

    }


    fun insertFavoritePhoto(imageItem: ImageItem) {
        viewModelScope.launch {
            repository.insertNewFavoritePhoto(imageItem)
        }
    }


}