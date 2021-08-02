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
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.kodein.db.*
import org.kodein.db.inmemory.inMemory
import org.kodein.db.orm.kotlinx.KotlinxSerializer
import org.kodein.memory.util.UUID

class ApplicationTest {
    private fun createDatabase(factory: DBFactory<DB>, dir: String): DB =
        factory.open(
            "$dir/test-db",
            KotlinxSerializer(),
            TypeTable {
                root<User>()
            }
        )

    private lateinit var db: DB

    @Before
    fun setup() {
        db = createDatabase(DB.inMemory, "fake")
    }

    /**
     *  Successful State
     */
    @Test
    fun whenRegistrationSuccessful_shouldBeAbleToLogin() = testApplication {
        val authRequest = AuthRequest("test12356", "test12345").toJson()
        post("/auth/register", authRequest).let { response ->
            val body: AuthResponse = response.content.toModel()

            Assert.assertEquals(State.SUCCESS, body.status)
            Assert.assertEquals("Registration successful", body.message)
            Assert.assertNotEquals(null, body.token)
        }

        post("/auth/login", authRequest).let { response ->
            val body: AuthResponse = response.content.toModel()

            Assert.assertEquals(State.SUCCESS, body.status)
            Assert.assertEquals("Login successful", body.message)
            Assert.assertNotEquals(null, body.token)
        }
    }

    /**
     *  Failed State
     */
    @Test
    fun whenPassedInvalidUserCredentials_responseStateShouldBeFailed() = testApplication {
        post("/auth/register", AuthRequest("test1", "test1").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()

            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals(null, body.token)
        }
    }

    @Test
    fun whenPassedUsernameAlreadyExists_responseStateShouldBeFailed() = testApplication {
        val authRequest = AuthRequest("test123", "test12345").toJson()
        post("/auth/register", authRequest)

        post("/auth/register", authRequest).content.toModel<AuthResponse>().let { response ->
            Assert.assertEquals(State.FAILED, response.status)
            Assert.assertEquals("Username is not available", response.message)
            Assert.assertEquals(null, response.token)
        }
    }

    @Test
    fun whenCredentialsAreIllegal_responseStateShouldBeFailed() = testApplication {

        // Register

        post("/auth/register", AuthRequest("test#", "test1234").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()
            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals("Username should not contain any special character", body.message)
            Assert.assertEquals(null, body.token)
        }

        post("/auth/register", AuthRequest("hi", "hi@12341").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()
            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals("Username should be of min 4 and max 32 character in length", body.message)
            Assert.assertEquals(null, body.token)
        }

        post("/auth/register", AuthRequest("test", "test").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()
            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals("Password should be of min 4 and max 32 character in length", body.message)
            Assert.assertEquals(null, body.token)
        }

        // Login

        post("/auth/login", AuthRequest("test#", "test1234").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()
            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals("Username should not contain any special character", body.message)
            Assert.assertEquals(null, body.token)
        }

        post("/auth/login", AuthRequest("hi", "hi@12341").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()
            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals("Username should be of min 4 and max 32 character in length", body.message)
            Assert.assertEquals(null, body.token)
        }

        post("/auth/login", AuthRequest("test", "test").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()
            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals("Password should be of min 4 and max 32 character in length", body.message)
            Assert.assertEquals(null, body.token)
        }
    }

    /**
     *  Unauthorized State
     */
    @Test
    fun whenPassedInvalidCredentials_responseStateShouldBeUnauthorized() = testApplication {
        post("/auth/login", AuthRequest("notExists", "qwerty123").toJson()).content.toModel<AuthResponse>().let { response ->
            Assert.assertEquals(State.UNAUTHORIZED, response.status)
            Assert.assertEquals("Invalid credentials", response.message)
            Assert.assertEquals(null, response.token)
        }
    }

    @After
    fun cleanup() {
        db.close()
    }

    private fun testApplication(test: TestApplicationEngine.() -> Unit) {
        withTestApplication(
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
