package com.segura.imagesapp.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.domain.useCase.GetImageDetailUseCase
import com.segura.imagesapp.model.photoDetail.PhotoDetailResponse
import com.segura.imagesapp.network.ResultWrapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    imageId: String,
    profileId: String,
    private val getImageDetailUseCase: GetImageDetailUseCase
) :
    ViewModel() {


    private val _imageInfo = MutableLiveData<Resource<PhotoDetailResponse>>()
    val imageInfo: LiveData<Resource<PhotoDetailResponse>> get() = _imageInfo

    init {
        getPhotoDetails(imageId)
    }

    private fun getPhotoDetails(imageId: String) {
        viewModelScope.launch {
            getImageDetailUseCase.execute(imageId)
                .collect {
                    _imageInfo.postValue(it)
                }
        }
    }


}