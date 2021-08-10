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

@Serializable
data class DetailResponse(
    override val status: State,
    override val message: String,
    val article: Article? = null
) : BaseResponse {
    companion object {
        fun failed(message: String) = DetailResponse(
            State.FAILED,
            message
        )

        fun success(article: Article) = DetailResponse(
            State.SUCCESS,
            "Successful",
            article
        )
    }
}
