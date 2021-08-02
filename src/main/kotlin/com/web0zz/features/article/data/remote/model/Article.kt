package com.web0zz.features.article.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: String,
    val title: String,
    val body: String,
    val writer: String,
    var article_category: String,
    val publish_time: String,
    val writer_image: String,
    val article_image: String
)
