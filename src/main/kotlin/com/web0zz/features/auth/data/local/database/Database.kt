package com.web0zz.features.auth.data.local.database

import com.web0zz.features.auth.data.local.const.ApplicationConst.Companion.DB_PATH
import com.web0zz.features.auth.data.local.model.User
import org.kodein.db.DB
import org.kodein.db.TypeTable
import org.kodein.db.impl.open
import org.kodein.db.inmemory.inMemory
import org.kodein.db.orm.kotlinx.KotlinxSerializer

lateinit var AuthDatabase: DB

fun initDatabase(testing: Boolean) {
    AuthDatabase = if(testing) {
        DB.inMemory.open(
            path = "test-db",
            TypeTable { root<User>() }, KotlinxSerializer()
        )
    } else {
        DB.open(
            DB_PATH,
            TypeTable { root<User>() }, KotlinxSerializer()
        )
    }
}

