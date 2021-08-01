package com.web0zz

import com.web0zz.features.auth.domain.AuthController
import io.ktor.application.*
import com.web0zz.plugins.*
import com.web0zz.features.auth.presentation.route.registerAuthRoutes
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    // Configures
    configureSerialization()
    configureKoin()
    configureJWT()

    val authController by inject<AuthController>()

    // Routes
    registerAuthRoutes(authController)
}
