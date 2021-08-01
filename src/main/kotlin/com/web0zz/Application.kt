package com.web0zz

import com.web0zz.controller.AuthController
import io.ktor.application.*
import com.web0zz.plugins.*
import com.web0zz.route.registerAuthRoutes
import org.kodein.db.DB
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    configureSerialization()
    configureKoin()

    val database: DB by inject()
    configureJWT(database)

    val authController: AuthController by inject()
    registerAuthRoutes(authController)
}
