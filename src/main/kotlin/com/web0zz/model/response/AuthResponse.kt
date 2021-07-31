package com.web0zz.model.response

data class AuthResponse (
    override val status: State,
    override val message: String,
    val token: String? = null
) : BaseResponse {

    companion object {

        fun failed(message: String) = AuthResponse(
            State.FAILED,
            message
        )

        fun unauthorized(message: String) = AuthResponse(
            State.UNAUTHORIZED,
            message
        )

        fun success(token: String, message: String) = AuthResponse(
            State.SUCCESS,
            message,
            token
        )
    }
}