package com.web0zz

import com.web0zz.features.article.data.remote.service.generator_url
import com.web0zz.features.article.domain.ArticleController
import com.web0zz.features.article.presentation.route.registerArticleRoute
import com.web0zz.features.auth.data.local.database.testing
import com.web0zz.features.auth.domain.AuthController
import com.web0zz.features.auth.presentation.route.registerAuthRoutes
import com.web0zz.plugins.*
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    with(ConfigUtil(environment.config)) {
        generator_url = API_URL
        testing = TEST_STATE.toBoolean()
    }

    // Configures
    configureCORS()
    configureStatusPages()
    configureSerialization()
    configureKoin()
    configureJWT()

    val authController by inject<AuthController>()
    val articleController by inject<ArticleController>()

    // Routes
    registerAuthRoutes(authController)
    registerArticleRoute(articleController)
}
