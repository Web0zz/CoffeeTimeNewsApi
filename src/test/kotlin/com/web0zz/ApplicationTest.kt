package com.web0zz

import com.web0zz.features.auth.data.local.model.User
import com.web0zz.model.request.AuthRequest
import com.web0zz.model.response.AuthResponse
import com.web0zz.model.response.State
import com.web0zz.util.post
import com.web0zz.util.toJson
import com.web0zz.util.toModel
import io.ktor.config.*
import io.ktor.server.testing.*
import io.ktor.util.*
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.kodein.db.DB
import org.kodein.db.DBFactory
import org.kodein.db.TypeTable
import org.kodein.db.inmemory.inMemory
import org.kodein.db.orm.kotlinx.KotlinxSerializer
import org.kodein.memory.util.UUID
import kotlin.test.AfterTest

class ApplicationTest {
    private fun createDatabase(factory: DBFactory<DB>, dir: String): DB =
        factory.open(
            "$dir/app-db",
            KotlinxSerializer(),
            TypeTable {
                root<User>()
            }
        )

    lateinit var db: DB

    @Before
    fun setup() {
        db = createDatabase(DB.inMemory, "fake")
    }

    @Test
    fun whenPassedInvalidUserCredentials_responseStateShouldBeFailed() = testApplication {
        post("/auth/register", AuthRequest("test1", "test1").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()

            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals(null, body.token)
        }
    }

    @AfterTest
    fun cleanup() {
        db.close()
    }

    private fun testApplication(test: TestApplicationEngine.() -> Unit) {
        withTestApplication (
            {
                (environment.config as MapApplicationConfig).apply {
                    put("jwt.secret", UUID.randomUUID().toString())
                }
                module()
            },
            test
        )
    }
}