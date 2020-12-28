package com.segura.imagesapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segura.imagesapp.dataSource.ImagesRepository
import com.segura.imagesapp.model.photoDetail.PhotoDetailResponse
import com.segura.imagesapp.network.ResultWrapper
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    imageId: String,
    profileId: String,
    private val repository: ImagesRepository
) :
    ViewModel() {

    private val _photoDetailInfo = MutableLiveData<PhotoDetailResponse>()
    val photoDetailInfo: LiveData<PhotoDetailResponse> get() = _photoDetailInfo

    init {
        getPhotoDetails(imageId)
    }

    private fun getPhotoDetails(imageId: String) {
        viewModelScope.launch {
            val result = repository.getPhotoDetails(imageId)
            when (result) {
                is ResultWrapper.Success -> {
                    _photoDetailInfo.postValue(result.value)
                }
                is ResultWrapper.NetworkError -> {
//                    Handler error here
                }
                is ResultWrapper.ApiError -> {
//                    Handler error here
                }
            }
        }
    }


}