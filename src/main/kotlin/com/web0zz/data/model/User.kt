package com.web0zz.data.model

import kotlinx.serialization.Serializable
import org.kodein.db.model.orm.Metadata
import org.kodein.memory.util.UUID


@Serializable
data class User (
    override val id: UUID,
    val username: String,
    val password: String
) : Metadata