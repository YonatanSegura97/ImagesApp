package com.segura.imagesapp.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.network.NetworkState
import com.segura.imagesapp.network.pagging.search.SearchImageDataSourceFactory
import com.segura.imagesapp.utils.getPagingConfig
import kotlinx.coroutines.launch

class SearchViewModel(private val repositoryOld: ImagesRepositoryOld) : ViewModel() {
    private val searchImageDataSource =
        SearchImageDataSourceFactory(
            repositoryOld = repositoryOld,
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
            repositoryOld.insertNewFavoritePhoto(imageItem)
        }
    }

}