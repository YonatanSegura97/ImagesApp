package com.segura.imagesapp.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse (

	@SerializedName("errors")
	val errors : List<String>
)