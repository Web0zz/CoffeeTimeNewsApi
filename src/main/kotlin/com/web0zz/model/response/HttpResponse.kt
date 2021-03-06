package com.web0zz.model.response

import io.ktor.http.HttpStatusCode

/**
 * Represents HTTP response which will be exposed via API.
 */
sealed class HttpResponse<T : BaseResponse> {
    abstract val body: T
    abstract val code: HttpStatusCode

    data class Ok<T : BaseResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.OK
    }

    data class NotFound<T : BaseResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.NotFound
    }

    data class BadRequest<T : BaseResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.BadRequest
    }

    data class Unauthorized<T : BaseResponse>(override val body: T) : HttpResponse<T>() {
        override val code: HttpStatusCode = HttpStatusCode.Unauthorized
    }

    companion object {
        fun <T : BaseResponse> ok(response: T) = Ok(body = response)

        fun <T : BaseResponse> notFound(response: T) = NotFound(body = response)

        fun <T : BaseResponse> badRequest(response: T) = BadRequest(body = response)

        fun <T : BaseResponse> unAuth(response: T) = Unauthorized(body = response)
    }
}

fun generateHttpResponse(response: BaseResponse): HttpResponse<BaseResponse> {
    return when (response.status) {
        State.SUCCESS -> HttpResponse.ok(response)
        State.NOT_FOUND -> HttpResponse.notFound(response)
        State.FAILED -> HttpResponse.badRequest(response)
        State.UNAUTHORIZED -> HttpResponse.unAuth(response)
    }
}
