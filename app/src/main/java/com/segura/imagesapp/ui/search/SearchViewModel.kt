package com.segura.imagesapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.segura.imagesapp.dataSource.ImagesRepository
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.network.NetworkState
import com.segura.imagesapp.network.pagging.search.SearchImageDataSourceFactory
import com.segura.imagesapp.utils.getPagingConfig
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ImagesRepository) : ViewModel() {
    private val searchImageDataSource =
        SearchImageDataSourceFactory(
            repository = repository,
            coroutineScope = viewModelScope,
            query = ""
        )
    var imageList: LiveData<PagedList<ImageItem>>? = null
    var networkState: LiveData<NetworkState>? = null
    var initialNetworkState: LiveData<NetworkState>? = null

    init {
        initialNetworkState =
            Transformations.switchMap(searchImageDataSource.source) { it.getInitialNetworkState() }
        networkState =
            Transformations.switchMap(searchImageDataSource.source) { it.getNetworkState() }
        imageList = LivePagedListBuilder(searchImageDataSource, getPagingConfig()).build()
    }

    fun updateQuery(query: String) {
        searchImageDataSource.updateQuery(query)
    }


    fun insertFavoritePhoto(imageItem: ImageItem) {
        viewModelScope.launch {
            repository.insertNewFavoritePhoto(imageItem)
        }
    }

}