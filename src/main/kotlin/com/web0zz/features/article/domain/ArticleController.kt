package com.web0zz.features.article.domain

import com.web0zz.features.article.data.remote.service.ArticleDatasource
import com.web0zz.model.response.ArticleResponse
import com.web0zz.model.response.DetailResponse

class ArticleController(private val articleDatasource: ArticleDatasource) {

    suspend fun requestArticle(category: String): ArticleResponse {
        return try {
            val article = articleDatasource.getArticle()
            article.forEach { item ->
                item.article_category = category
            }

            ArticleResponse.success(article)
        } catch (nullData: NullPointerException) {
            ArticleResponse.failed("Couldn't find any article")
        } catch (e: Exception) {
            ArticleResponse.failed("We couldn't fetch data: ${e.message ?: "Something's went wrong"}")
        }
    }

    /**
     * Datasource works by sending random data. For this reason, it does not have recorded data.
     */
    suspend fun requestArticleById(id: String): DetailResponse {
        return try {
            val article = articleDatasource.getArticle().first()

            DetailResponse.success(article)
        } catch (nullData: NullPointerException) {
            DetailResponse.failed("Couldn't find the article")
        } catch (e: Exception) {
            DetailResponse.failed("We couldn't fetch data: ${e.message ?: "Something's went wrong"}")
        }
    }
}
