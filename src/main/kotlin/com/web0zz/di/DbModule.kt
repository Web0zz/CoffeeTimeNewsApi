package com.web0zz.di

import com.web0zz.data.local.dao.UserDao
import com.web0zz.data.local.database.db
import org.koin.dsl.module

val databaseModule = module {
    single { db }
    single { UserDao(get()) }
}