package com.segura.imagesapp.utils

import com.segura.imagesapp.domain.model.ProfileImage
import com.segura.imagesapp.domain.model.User

private val imageProfileTest = ProfileImage(
    large = "url",
    medium = "url",
    small = "url"

)
val userDetailTest = User(
    acceptedTos = true,
    bio = "empty bio",
    firstName = "Yonatan",
    id = "123",
    instagramUsername = "Yontanseg97",
    lastName = "Segura",
    location = "Chalatenango, El Salvador",
    name = "Fake Name",
    portfolioUrl = "url",
    profileImage = imageProfileTest,
    totalCollections = 20,
    totalLikes = 20,
    totalPhotos = 20,
    twitterUsername = "Yonatanseg97",
    updatedAt = "13:00:00",
    username = "yonseg97"

)