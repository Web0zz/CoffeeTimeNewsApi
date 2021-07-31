package com.web0zz.controller

import com.web0zz.auth.JwtConfig
import com.web0zz.data.dao.UserDao
import com.web0zz.model.response.AuthResponse
import io.ktor.features.*

class AuthController constructor(private val userDao: UserDao){

    private val jwt = JwtConfig.instance

    fun register(username: String, password: String): AuthResponse {
        return try {
            validateCredentials(username, password)

            if(!userDao.isUsernameAvailable(username)) {
                throw BadRequestException("Username is not available")
            }

            val user = userDao.addUser(username, password)
            AuthResponse.success(jwt.sign(user.id.toString()), "Registration successful")
        } catch (b: BadRequestException) {
            AuthResponse.failed(b.message ?: "Failed while registration")
        }
    }


    private fun validateCredentials(username: String, password: String) {
        val message = when {
            (username.isBlank() or password.isBlank()) -> "Username or password should not be blank"
            (username.length !in (4..32)) -> "Username should be of min 4 and max 32 character in length"
            (password.length !in (8..64)) -> "Password should be of min 4 and max 32 character in length"
            (!username.isAlphaNumeric()) -> "Username should not contain any special character"
            else -> return
        }
        throw BadRequestException(message)
    }
}

private fun String.isAlphaNumeric(): Boolean {
    // TODO will check not contain special character
    return true
}
