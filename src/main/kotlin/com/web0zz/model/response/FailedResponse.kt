package com.web0zz.model.response

data class FailedResponse(override val status: State, override val message: String) : BaseResponse