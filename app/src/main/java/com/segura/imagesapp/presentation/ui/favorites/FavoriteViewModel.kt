package com.segura.imagesapp.presentation.ui.favorites

import androidx.lifecycle.*
import com.segura.imagesapp.domain.useCase.DeleteFavoriteImageUseCase
import com.segura.imagesapp.domain.useCase.GetFavoriteImagesUseCase
import com.segura.imagesapp.domain.useCase.SearchFavoriteImagesUseCase
import com.segura.imagesapp.model.ImageItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class FavoriteViewModel(
    getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val deleteFavoriteImageUseCase: DeleteFavoriteImageUseCase,
    private val searchFavoriteImagesUseCase: SearchFavoriteImagesUseCase
) :
    ViewModel() {

    val favoritePhotos: Flow<List<ImageItem>> = getFavoriteImagesUseCase.execute()

    private val _filterImages = MutableLiveData<List<ImageItem>>()
    val filterImages: LiveData<List<ImageItem>> get() = _filterImages

    fun deleteFavoritePhoto(item: ImageItem) {
        viewModelScope.launch {
            deleteFavoriteImageUseCase.execute(item)
        }
    }

    fun getFilterImages(query: String) {
        viewModelScope.launch {
            _filterImages.postValue(searchFavoriteImagesUseCase.execute(query))
        }
    }

}