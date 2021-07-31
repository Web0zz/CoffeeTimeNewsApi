package com.web0zz.model.response

interface BaseResponse {
    val status: State
    val message: String
}

enum class State {
    SUCCESS, NOT_FOUND, FAILED, UNAUTHORIZED
}
