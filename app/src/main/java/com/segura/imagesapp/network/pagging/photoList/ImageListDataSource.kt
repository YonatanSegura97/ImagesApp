package com.segura.imagesapp.network.pagging.photoList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.domain.model.NetworkState
import com.segura.imagesapp.model.ImageItem
import com.segura.imagesapp.network.ResultWrapper
import com.segura.imagesapp.utils.ConstantsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class ImageListDataSource(
    private val coroutineScope: CoroutineScope,
    private val repositoryOld: ImagesRepositoryOld
) :
    PageKeyedDataSource<Int, ImageItem>() {

    //Fot handler the State ----
    private val networkState = MutableLiveData<NetworkState>()
    private val initialNetworkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Any)? = null


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageItem>
    ) {
        retryQuery = { loadInitial(params, callback) }
        coroutineScope.launch {
            initialNetworkState.postValue(RUNNING)
            when (val result = repositoryOld.getImageList(1, ConstantsUtils.ITEMS_BY_PAGE)) {
                is ResultWrapper.ApiError -> {
                    initialNetworkState.postValue(ERROR)
                }
                is ResultWrapper.NetworkError -> {
                    initialNetworkState.postValue(ERROR)
                }

                is ResultWrapper.Success -> {
                    callback.onResult(result.value, null, 2)
                    initialNetworkState.postValue(LOADED)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {
        retryQuery = { loadAfter(params, callback) }
        coroutineScope.launch {
            networkState.postValue(RUNNING)
            when (val result = repositoryOld.getImageList(params.key, ConstantsUtils.ITEMS_BY_PAGE)) {
                is ResultWrapper.ApiError -> {
                    networkState.postValue(ERROR)
                }
                is ResultWrapper.NetworkError -> {
                    networkState.postValue(ERROR)
                }

                is ResultWrapper.Success -> {
                    callback.onResult(result.value, params.key + 1)
                    networkState.postValue(LOADED)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {}

    //Public
    fun getNetworkState(): LiveData<NetworkState> = networkState

    fun getInitialNetworkState(): LiveData<NetworkState> = initialNetworkState

    fun refresh() = this.invalidate()

    fun retryFailedQuery() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

    companion object {
        val LOADED = NetworkState.SUCCESS
        val RUNNING = NetworkState.RUNNING
        val ERROR = NetworkState.FAILED
    }
}