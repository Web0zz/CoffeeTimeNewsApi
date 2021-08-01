package com.web0zz.model.response

import kotlinx.serialization.Serializable

@Serializable
data class FailedResponse(override val status: State, override val message: String) : BaseResponse