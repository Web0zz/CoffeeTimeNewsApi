package com.web0zz.features.article.presentation.route

import com.web0zz.features.article.domain.ArticleController
import com.web0zz.model.exception.FailMessage
import com.web0zz.model.exception.UnAuthorizedAccessException
import com.web0zz.model.response.generateHttpResponse
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerArticleRoute(articleController: ArticleController) {
    routing {
        articleRoute(articleController)
    }
}

fun Route.articleRoute(articleController: ArticleController) {

    route("/article") {
        authenticate {
            get("/{category}") {
                val category = call.parameters["category"] ?: return@get
                call.principal<UserIdPrincipal>()
                    ?: throw UnAuthorizedAccessException(FailMessage.MESSAGE_ACCESS_DENIED)

                val articleResponse = articleController.requestArticle(category)
                val response = generateHttpResponse(articleResponse)

                call.respond(response.code, response.body)
            }
        }
    }
}
