package com.web0zz.plugins

import com.web0zz.di.controllerModule
import com.web0zz.di.databaseModule
import io.ktor.application.*
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(databaseModule, controllerModule)
    }
}
