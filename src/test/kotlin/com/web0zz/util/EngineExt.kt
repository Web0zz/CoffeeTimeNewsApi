package com.web0zz.util

import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody

fun TestApplicationEngine.get(url: String, token: String? = null) =
    getResponse(HttpMethod.Get, url, null, token)

fun TestApplicationEngine.put(url: String, body: String?, token: String? = null) =
    getResponse(HttpMethod.Put, url, body, token)

fun TestApplicationEngine.post(url: String, body: String?, token: String? = null) =
    getResponse(HttpMethod.Post, url, body, token)

fun TestApplicationEngine.delete(url: String, token: String? = null) =
    getResponse(HttpMethod.Delete, url, null, token)

fun TestApplicationEngine.getResponse(
    method: HttpMethod,
    url: String,
    body: String? = null,
    token: String? = null
) = handleRequest(method, url) {
    if (method != HttpMethod.Get) {
        addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        body?.let { setBody(it) }
    }
    token?.let { addHeader(HttpHeaders.Authorization, it) }
}.response