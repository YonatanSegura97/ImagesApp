package com.segura.imagesapp.presentation.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.model.photoDetail.PhotoDetailResponse
import com.segura.imagesapp.network.ResultWrapper
import kotlinx.coroutines.launch

class PhotoDetailViewModel(
    imageId: String,
    profileId: String,
    private val repositoryOld: ImagesRepositoryOld
) :
    ViewModel() {

    private val _photoDetailInfo = MutableLiveData<PhotoDetailResponse>()
    val photoDetailInfo: LiveData<PhotoDetailResponse> get() = _photoDetailInfo

    init {
        getPhotoDetails(imageId)
    }

    private fun getPhotoDetails(imageId: String) {
        viewModelScope.launch {
            val result = repositoryOld.getPhotoDetails(imageId)
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