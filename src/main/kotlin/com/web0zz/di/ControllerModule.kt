package com.web0zz.di

import com.web0zz.controller.AuthController
import org.koin.dsl.module

val controllerModule = module {
    single { AuthController(get()) }
}