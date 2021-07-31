package com.web0zz.model.response

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: String,
    val title: String,
    val body: String,
    val writer: String,
    val article_category: String,
    val publish_time: String,
    val writer_image: String,
    val article_image: String
) {

}

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

        fun success(articles: List<Article>) = ArticleResponse(
            State.SUCCESS,
            "Successful",
            articles
        )
    }
}
