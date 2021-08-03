package com.web0zz.features.article.data.remote.service

import com.web0zz.features.article.data.remote.model.Article
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*

var generator_url = "https://ktor.io/"

class ArticleDatasource {
    suspend fun getArticle(): List<Article> {
        HttpClient() {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }.use { client ->
            return client.get(generator_url)
        }
    }
}
