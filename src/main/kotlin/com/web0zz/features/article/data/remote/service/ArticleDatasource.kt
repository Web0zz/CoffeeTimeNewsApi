package com.web0zz.features.article.data.remote.service

import com.web0zz.features.article.data.remote.model.Article
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.utils.io.core.use

var generator_url = "https://ktor.io/"

class ArticleDatasource {
    suspend fun getArticle(): List<Article> {
        HttpClient().use { client ->
            return client.get(generator_url)
        }
    }
}
