package com.segura.imagesapp.presentation.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.model.ImageItem
import kotlinx.coroutines.launch


class FavoriteViewModel(private val repositoryOld: ImagesRepositoryOld) : ViewModel() {

    val favoritePhotos: LiveData<List<ImageItem>> = repositoryOld.favoritesPhotos

    private val _filterImages = MutableLiveData<List<ImageItem>>()
    val filterImages: LiveData<List<ImageItem>> get() = _filterImages

    fun deleteFavoritePhoto(item: ImageItem) {
        viewModelScope.launch {
            repositoryOld.deleteFavoritePhoto(item)
        }
    }

    fun getFilterImages(query: String) {
        viewModelScope.launch {
            _filterImages.postValue(repositoryOld.searchImages(query))
        }
    }

}