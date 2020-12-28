package com.segura.imagesapp.network.pagging.photoList

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.segura.imagesapp.dataSource.ImagesRepository
import com.segura.imagesapp.model.ImageItem
import kotlinx.coroutines.CoroutineScope

class ImageListDataSourceFactory(
    private val repository: ImagesRepository,
    private val coroutineScope: CoroutineScope
) : DataSource.Factory<Int, ImageItem>() {

    val source = MutableLiveData<ImageListDataSource>()
    override fun create(): DataSource<Int, ImageItem> {
        val source = ImageListDataSource(repository = repository, coroutineScope = coroutineScope)
        this.source.postValue(source)
        return source
    }
}