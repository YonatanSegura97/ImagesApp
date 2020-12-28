package com.segura.imagesapp.network.pagging.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.segura.imagesapp.dataSource.ImagesRepository
import com.segura.imagesapp.model.ImageItem

import kotlinx.coroutines.CoroutineScope

class SearchImageDataSourceFactory(
    private val repository: ImagesRepository,
    private val coroutineScope: CoroutineScope,
    private var query: String,
) : DataSource.Factory<Int, ImageItem>() {
    val source = MutableLiveData<SearchImageDataSource>()
    override fun create(): DataSource<Int, ImageItem> {
        val source = SearchImageDataSource(
            repository = repository,
            coroutineScope = coroutineScope,
            query = query
        )
        this.source.postValue(source)
        return source
    }

    fun updateQuery(newQuery: String) {
        this.query = newQuery
        getSource()?.refresh()
    }

    fun getSource() = source.value


}