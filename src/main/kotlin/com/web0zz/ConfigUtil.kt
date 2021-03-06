package com.web0zz

import io.ktor.config.*

class ConfigUtil constructor(config: ApplicationConfig) {
    val SECRET_KEY = config.property("jwt.secret").getString()
    val API_URL = config.property("mockaroo.url").getString()
    val TEST_STATE = config.property("sql.testing").getString()
}
