package com.web0zz.di

import com.web0zz.features.article.data.remote.service.ArticleDatasource
import com.web0zz.features.auth.data.local.dao.UserDao
import com.web0zz.features.auth.data.local.database.AuthDatabase
import org.koin.dsl.module

val daoModule = module {
    single { UserDao(AuthDatabase) }
    single { ArticleDatasource() }
}