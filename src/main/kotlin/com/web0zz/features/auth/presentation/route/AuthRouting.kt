package com.web0zz.features.auth.presentation.route

import com.web0zz.features.auth.domain.AuthController
import com.web0zz.model.exception.BadRequestException
import com.web0zz.model.exception.FailMessage
import com.web0zz.model.request.AuthRequest
import com.web0zz.model.response.generateHttpResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerAuthRoutes(authController: AuthController) {
    routing {
        authUser(authController)
    }
}

fun Route.authUser(authController: AuthController) {

    route("/auth") {
        post("/register") {
            val authRequest = runCatching { call.receive<AuthRequest>() }.getOrElse {
                throw BadRequestException(FailMessage.MESSAGE_MISSING_USER_CREDENTIALS)
            }

            val authResponse = authController.register(authRequest.username, authRequest.password)
            val response = generateHttpResponse(authResponse)

            call.respond(response.code, response.body)
        }

        post("/login") {
            val authRequest = runCatching { call.receive<AuthRequest>() }.getOrElse {
                throw BadRequestException(FailMessage.MESSAGE_MISSING_USER_CREDENTIALS)
            }

            val authResponse = authController.login(authRequest.username, authRequest.password)
            val response = generateHttpResponse(authResponse)

            call.respond(response.code, response.body)
        }
    }
}
