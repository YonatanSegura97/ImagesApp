package com.segura.imagesapp.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segura.imagesapp.data.repository.ImagesRepositoryOld
import com.segura.imagesapp.domain.model.Resource
import com.segura.imagesapp.domain.useCase.GetUserDetailUseCase
import com.segura.imagesapp.model.User
import com.segura.imagesapp.network.ResultWrapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileViewModel(userName: String, private val getUserDetailUseCase: GetUserDetailUseCase) :
    ViewModel() {


    private val _user = MutableLiveData<Resource<User>>()
    val user: LiveData<Resource<User>> get() = _user

    init {
        getUserProfile(userName)
    }

    private fun getUserProfile(userName: String) {
        viewModelScope.launch {
            getUserDetailUseCase.execute(userName).collect {
                _user.postValue(it)
            }
        }
    }
}