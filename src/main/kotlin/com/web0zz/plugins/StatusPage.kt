package com.web0zz.plugins

import com.web0zz.model.exception.FailMessage
import com.web0zz.model.response.FailedResponse
import com.web0zz.model.response.State
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> {
            call.respond(HttpStatusCode.BadRequest, FailedResponse(State.FAILED, it.message ?: "Bad Request"))
        }

        status(HttpStatusCode.InternalServerError) {
            call.respond(it, FailedResponse(State.FAILED, FailMessage.MESSAGE_FAILED))
        }

        status(HttpStatusCode.Unauthorized) {
            call.respond(it, FailedResponse(State.UNAUTHORIZED, FailMessage.MESSAGE_ACCESS_DENIED))
        }
    }
}
