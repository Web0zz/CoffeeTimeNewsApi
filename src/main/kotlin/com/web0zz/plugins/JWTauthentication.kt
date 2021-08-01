package com.web0zz.plugins

import com.web0zz.ConfigUtil
import com.web0zz.config.JwtConfig
import com.web0zz.features.auth.data.local.dao.UserDao
import com.web0zz.features.auth.data.local.database.AuthDatabase
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.kodein.db.DB

fun Application.configureJWT(db: DB = AuthDatabase) {
    with(ConfigUtil(environment.config)) {
        JwtConfig.initialize(SECRET_KEY)
    }

    install(Authentication) {
        jwt {
            verifier(JwtConfig.instance.verifier)
            validate {
                val claim = it.payload.getClaim(JwtConfig.CLAIM).asString()
                if (claim.let(UserDao(db)::isUserExists)) {
                    UserIdPrincipal(claim)
                } else {
                    null
                }
            }
        }
    }
}