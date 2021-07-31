package com.web0zz.data.local.dao

import com.web0zz.data.local.model.User
import com.web0zz.data.local.util.hash
import org.kodein.db.DB
import org.kodein.db.find
import org.kodein.memory.util.UUID

class UserDao constructor(private val db: DB){

    fun addUser(username: String, password: String): User {
        val user = User(
            UUID.randomUUID(),
            username,
            password.hash()
        )
        db.put(user)

        return user
    }

    fun isUsernameAvailable(username: String): Boolean {
        db.find<User>().byIndex("username").use { cursor ->
            while (cursor.isValid()) {
                val model = cursor.model()
                if (model.username == username) return true
                else cursor.next()
            }
        }
        return false
    }

    fun getByUsernameAndPassword(username: String, password: String): User? {
        db.find<User>().byIndex("username").use { cursor ->
            while (cursor.isValid()) {
                val model = cursor.model()
                if (model.username == username && model.password == password.hash()) return model
                else cursor.next()
            }
        }

        return null
    }
}