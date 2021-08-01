package com.web0zz.di

import com.web0zz.features.auth.domain.AuthController
import org.koin.dsl.module

val controllerModule = module {
    single { AuthController(get()) }
}