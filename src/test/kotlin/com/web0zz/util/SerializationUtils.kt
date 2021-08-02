package com.web0zz.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> T.toJson(): String = Json.encodeToString(this)

inline fun <reified T> String?.toModel(): T = this!!.let { json -> Json.decodeFromString(json) }
