package com.segura.imagesapp.network.pagging.search

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.model.ImageItem

import kotlinx.coroutines.CoroutineScope

class SearchImageDataSourceFactory(
    private val repositoryOld: ImagesRepositoryOld,
    private val coroutineScope: CoroutineScope,
    private var query: String,
) : DataSource.Factory<Int, ImageItem>() {
    val source = MutableLiveData<SearchImageDataSource>()
    override fun create(): DataSource<Int, ImageItem> {
        val source = SearchImageDataSource(
            repositoryOld = repositoryOld,
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