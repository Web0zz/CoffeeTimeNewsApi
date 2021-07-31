package com.web0zz.route

import com.web0zz.controller.AuthController
import com.web0zz.exception.BadRequestException
import com.web0zz.exception.FailMessage
import com.web0zz.model.request.AuthRequest
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.db.DB

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
            // TODO HTTP generator needed here

            call.respond(HttpStatusCode.Created, authResponse.message)
        }

        post("/login") {
            val authRequest = runCatching { call.receive<AuthRequest>() }.getOrElse {
                throw BadRequestException(FailMessage.MESSAGE_MISSING_USER_CREDENTIALS)
            }

            val authResponse = authController.login(authRequest.username, authRequest.password)
            // TODO HTTP generator needed here

            call.respond(HttpStatusCode.Created, authResponse.message)
        }
    }
}
