package com.web0zz.features.auth.data.local.database

import com.web0zz.features.auth.data.local.const.ApplicationConst.Companion.DB_PATH
import com.web0zz.features.auth.data.local.model.User
import org.kodein.db.DB
import org.kodein.db.DBFactory
import org.kodein.db.TypeTable
import org.kodein.db.impl.open
import org.kodein.db.inmemory.inMemory
import org.kodein.db.orm.kotlinx.KotlinxSerializer

var testing = false

val AuthDatabase = initDatabase()

private fun initDatabase(): DB {
    return if(!testing) {
        DB.open(
            DB_PATH,
            TypeTable { root<User>() }, KotlinxSerializer()
        )
    } else {
        DB.inMemory.open(
            path = "test-db",
            TypeTable { root<User>() }, KotlinxSerializer()
        )
    }
}
