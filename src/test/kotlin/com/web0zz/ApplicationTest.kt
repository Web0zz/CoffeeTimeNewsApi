package com.web0zz

import com.web0zz.model.exception.BadRequestException
import com.web0zz.model.exception.FailMessage
import com.web0zz.model.request.AuthRequest
import com.web0zz.model.response.ArticleResponse
import com.web0zz.model.response.AuthResponse
import com.web0zz.model.response.State
import com.web0zz.util.get
import com.web0zz.util.post
import com.web0zz.util.toJson
import com.web0zz.util.toModel
import io.ktor.config.*
import io.ktor.server.testing.*
import org.junit.*
import org.kodein.memory.util.UUID

class ApplicationTest {

    /**
     *  Testing Article Route
     *  Successful State
     */
    @Test
    fun whenRegistrationSuccessful_shouldBeAbleToLogin() = testApplication {
        val authRequest = AuthRequest("test123456", "test123456").toJson()
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
    fun whenProvidedInvalidUserCredentials_responseStateShouldBeFailed() = testApplication {
        post("/auth/register", AuthRequest("test1", "test1").toJson()).let { response ->
            val body: AuthResponse = response.content.toModel()

            Assert.assertEquals(State.FAILED, body.status)
            Assert.assertEquals(null, body.token)
        }
    }

    @Test
    fun whenProvidedUsernameAlreadyExists_responseStateShouldBeFailed() = testApplication {
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
    fun whenProvidedInvalidCredentials_responseStateShouldBeUnauthorized() = testApplication {
        post("/auth/login", AuthRequest("notExists", "qwerty123").toJson()).content.toModel<AuthResponse>().let { response ->
            Assert.assertEquals(State.UNAUTHORIZED, response.status)
            Assert.assertEquals("Invalid credentials", response.message)
            Assert.assertEquals(null, response.token)
        }
    }

    /**
     *  Exception
     */
    @Test
    fun whenProvidedInvalidAuthBody_shouldThrowException() = testApplication {
        try {
            post("/auth/register", null)
            Assert.assertEquals(false, true)
        } catch (b: BadRequestException) {
            Assert.assertEquals(FailMessage.MESSAGE_MISSING_USER_CREDENTIALS, b.message)
        }

        try {
            post("/auth/login", null)
            Assert.assertEquals(false, true)
        } catch (b: BadRequestException) {
            Assert.assertEquals(FailMessage.MESSAGE_MISSING_USER_CREDENTIALS, b.message)
        }
    }

    /**
     *  Testing Article Route
     */
    @Test
    fun whenAuthorizationTokenIsNotProvided_responseStateShouldBeUnauthorized() = testApplication {
        val article = get("/article/health").content ?: ""
        Assert.assertEquals(true, article.contains("UNAUTHORIZED"))
    }

    @Test
    fun whenProvidedInvalidArticleCategory_shouldThrowException() = testApplication {
        val token = post(
            "/auth/register",
            AuthRequest("user321", "passwd321").toJson()
        ).content.toModel<AuthResponse>().token

        try {
            get("/article/", "Bearer $token")

            // Checking is it successful at catching BadRequestException
            Assert.assertEquals(false, true)
        } catch (b: BadRequestException) {
            Assert.assertEquals(FailMessage.MESSAGE_MISSING_ARTICLE_CATEGORY, b.message)
        }
    }

    @Test
    fun whenUserIsAuthenticated_shouldBeAbleToGetArticles() = testApplication {
        val token = post(
            "/auth/register",
            AuthRequest("username543", "password543").toJson()
        ).content.toModel<AuthResponse>().token

        get("/article/health", "Bearer $token").content.toModel<ArticleResponse>().let { response ->
            Assert.assertEquals(State.SUCCESS, response.status)
            Assert.assertEquals(true, response.articles.isNotEmpty())
        }
    }


    private fun testApplication(test: TestApplicationEngine.() -> Unit) {
        withTestApplication(
            {
                (environment.config as MapApplicationConfig).apply {
                    put("jwt.secret", UUID.randomUUID().toString())
                    put("mockaroo.url", "https://my.api.mockaroo.com/article.json?key=10ab3890")
                    put("sql.testing", "true")
                }
                module()
            },
            test
        )
    }
}