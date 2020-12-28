package com.segura.imagesapp.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segura.imagesapp.dataSource.ImagesRepository
import com.segura.imagesapp.model.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.launch


class DashboardViewModel(private val repository: ImagesRepository) : ViewModel() {

    val favoritePhotos: LiveData<List<ImageItem>> = repository.favoritesPhotos

    private val _filterImages = MutableLiveData<List<ImageItem>>()
    val filterImages: LiveData<List<ImageItem>> get() = _filterImages

    fun deleteFavoritePhoto(item: ImageItem) {
        viewModelScope.launch {
            repository.deleteFavoritePhoto(item)
        }
    }

    fun getFilterImages(query: String) {
        viewModelScope.launch {
            _filterImages.postValue(repository.searchImages(query))
        }
    }

}