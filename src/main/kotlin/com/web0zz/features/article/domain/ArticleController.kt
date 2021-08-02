package com.web0zz.features.article.domain

import com.web0zz.features.article.data.remote.service.ArticleDatasource
import com.web0zz.model.response.ArticleResponse

class ArticleController(private val articleDatasource: ArticleDatasource) {

    suspend fun requestArticle(): ArticleResponse {
        return try {
            val article = articleDatasource.getArticle()

            ArticleResponse.success(article)
        } catch (nullData: NullPointerException) {
            ArticleResponse.failed("Couldn't find any article")
        } catch (e: Exception) {
            ArticleResponse.failed("We couldn't fetch data: ${e.message ?: "Something's went wrong"}")
        }
    }
}