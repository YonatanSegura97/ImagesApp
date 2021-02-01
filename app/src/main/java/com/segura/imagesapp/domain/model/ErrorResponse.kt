package com.segura.imagesapp.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse (

	@SerializedName("errors")
	val errors : List<String>
)