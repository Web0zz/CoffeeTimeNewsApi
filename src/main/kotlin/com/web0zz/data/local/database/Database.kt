package com.web0zz.data.local.database

import com.web0zz.const.ApplicationConst.Companion.DB_PATH
import com.web0zz.data.local.model.User
import org.kodein.db.DB
import org.kodein.db.TypeTable
import org.kodein.db.impl.open
import org.kodein.db.orm.kotlinx.KotlinxSerializer

val db = initDatabase()

private fun initDatabase(): DB =
    DB.open(DB_PATH,
        TypeTable { root<User>() }, KotlinxSerializer()
    )
