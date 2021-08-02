package com.web0zz.features.auth.data.local.dao

import com.web0zz.features.auth.data.local.model.User
import com.web0zz.features.auth.data.local.util.hash
import org.kodein.db.DB
import org.kodein.db.find
import org.kodein.db.useModels
import org.kodein.memory.util.UUID

class UserDao(private val db: DB) {

    fun addUser(username: String, password: String): User {
        val user = User(
            UUID.randomUUID().toString(),
            username,
            password.hash()
        )
        db.put(user)

        return user
    }

    fun isUsernameAvailable(username: String): Boolean {
        db.find<User>().all().useModels {
            it.forEach { user ->
                if (user.username == username) return true
            }
        }
        return false
    }

    fun isUserExists(id: String): Boolean {
        return db.find<User>().byId(id).isValid()
    }

    fun getByUsernameAndPassword(username: String, password: String): User? {
        db.find<User>().all().useModels {
            it.forEach { user ->
                if (user.username == username && user.password == password.hash()) return user
            }
        }
        return null
    }
}
