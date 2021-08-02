package com.web0zz.model.response

import com.web0zz.features.article.data.remote.model.Article
import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    override val status: State,
    override val message: String,
    val articles: List<Article> = emptyList()
) : BaseResponse {
    companion object {
        fun unauthorized(message: String) = ArticleResponse(
            State.UNAUTHORIZED,
            message
        )

        fun failed(message: String) = ArticleResponse(
            State.FAILED,
            message
        )

        fun success(articles: List<Article>) = ArticleResponse(
            State.SUCCESS,
            "Successful",
            articles
        )
    }
}
