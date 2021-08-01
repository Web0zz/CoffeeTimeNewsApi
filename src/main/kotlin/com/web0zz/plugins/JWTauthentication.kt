package com.web0zz.plugins

import com.web0zz.auth.JwtConfig
import com.web0zz.data.local.dao.UserDao
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.kodein.db.DB

fun Application.configureJWT(db: DB) {
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