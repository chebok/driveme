package io.infrastructure.http

import io.delivery.http.shared.ErrorResponse
import io.domain.shared.DomainError
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.respondError(error: DomainError) {
    when (error) {
        is DomainError.Validation ->
            respond(
                message = ErrorResponse(error.code, error.message),
                status = HttpStatusCode.BadRequest,
            )
        is DomainError.NotFound ->
            respond(
                HttpStatusCode.NotFound,
                ErrorResponse(error.code, error.message),
            )
        is DomainError.Conflict ->
            respond(
                HttpStatusCode.Conflict,
                ErrorResponse(error.code, error.message),
            )
        is DomainError.Unauthorized ->
            respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(error.code, error.message),
            )
        is DomainError.BusinessRuleViolation ->
            respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(error.code, error.message),
            )
        is DomainError.Unexpected ->
            respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(error.code, error.message),
            )
        is DomainError.InvalidStateTransition ->
            respond(
                HttpStatusCode.Conflict,
                ErrorResponse(error.code, error.message),
            )
    }
}
