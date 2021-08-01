package com.web0zz.plugins

import com.web0zz.di.controllerModule
import com.web0zz.di.daoModule
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(koinModules: List<Module> = listOf(daoModule, controllerModule)) {
    install(Koin) {
        slf4jLogger()
        modules(koinModules)
    }
}
