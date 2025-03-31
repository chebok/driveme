package io.domain.shared

sealed interface DomainError {
    val code: String
    val message: String

    data class Validation(
        override val code: String = "validation_error",
        override val message: String,
    ) : DomainError

    data class BusinessRuleViolation(
        override val code: String = "business_rule_violation",
        override val message: String,
    ) : DomainError

    data class InvalidStateTransition(
        val from: String,
        val to: String,
    ) : DomainError {
        override val code: String = "invalid_state_transition"
        override val message: String = "Cannot transition from state '$from' to '$to'"
    }

    data class NotFound(
        val resource: String,
        val id: String,
    ) : DomainError {
        override val code: String = "not_found"
        override val message: String = "$resource with id=$id not found"
    }

    data class Conflict(
        override val message: String,
    ) : DomainError {
        override val code: String = "conflict"
    }

    data class Unauthorized(
        override val message: String = "Access denied",
    ) : DomainError {
        override val code: String = "unauthorized"
    }

    data class Unexpected(
        val exception: Throwable,
    ) : DomainError {
        override val code: String = "unexpected"
        override val message: String = exception.message ?: "Unexpected error"
    }
}
