package com.web0zz.features.article.presentation.route

import com.web0zz.features.article.domain.ArticleController
import com.web0zz.model.exception.BadRequestException
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
            get("/{category?}") {
                val category = call.parameters["category"] ?: throw BadRequestException(FailMessage.MESSAGE_MISSING_ARTICLE_CATEGORY)
                if (category.isBlank()) throw BadRequestException(FailMessage.MESSAGE_MISSING_ARTICLE_CATEGORY)
                call.principal<UserIdPrincipal>()
                    ?: throw UnAuthorizedAccessException(FailMessage.MESSAGE_ACCESS_DENIED)

                val articleResponse = articleController.requestArticle(category)
                val response = generateHttpResponse(articleResponse)

                call.respond(response.code, response.body)
            }

            get("/detail/{id?}") {
                val id = call.parameters["id"] ?: throw BadRequestException(FailMessage.MESSAGE_MISSING_ARTICLE_ID)
                if (id.isBlank()) throw BadRequestException(FailMessage.MESSAGE_MISSING_ARTICLE_ID)
                call.principal<UserIdPrincipal>()
                    ?: throw UnAuthorizedAccessException(FailMessage.MESSAGE_ACCESS_DENIED)

                val articleResponse = articleController.requestArticleById(id)
                val response = generateHttpResponse(articleResponse)

                call.respond(response.code, response.body)
            }
        }
    }
}
