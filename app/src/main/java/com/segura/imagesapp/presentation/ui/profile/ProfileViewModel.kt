package com.segura.imagesapp.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.model.User
import com.segura.imagesapp.network.ResultWrapper
import kotlinx.coroutines.launch

class ProfileViewModel(userName: String, private val repositoryOld: ImagesRepositoryOld) : ViewModel() {


    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    init {
        getUserProfile(userName)
    }

    private fun getUserProfile(userName: String) {
        viewModelScope.launch {
            val result = repositoryOld.getUserDetails(userName)
            when (result) {
                is ResultWrapper.ApiError -> {
//                    Handler Error
                }
                is ResultWrapper.NetworkError -> {
//                    Handler Error
                }
                is ResultWrapper.Success -> {
                    _user.postValue(result.value)
                }
            }
        }
    }
}