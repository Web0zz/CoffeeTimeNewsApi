package com.web0zz.route

import io.ktor.application.*
import io.ktor.routing.*
import org.kodein.db.DB

fun Application.registerAuthRoutes() {
    routing {
        authUser()
    }
}

fun Route.authUser() {
    route("/auth") {
        post("/register") {
        }

        post("/login") {
        }
    }
}
