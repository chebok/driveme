package io.domain.ride

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import io.domain.shared.DomainError
import java.util.*

class Ride(
    val id: UUID,
    val passengerId: UUID,
    val pickup: Location,
    val destination: Location,
    private var state: RideState,
) {
    val currentState: RideState
        get() = state

    fun accept() {
        require(state == RideState.Requested) {
            "Ride can only be accepted from 'Requested' state"
        }
        state = RideState.Accepted
    }

    fun start(): Either<DomainError, Ride> =
        if (state != RideState.Accepted) {
            DomainError
                .InvalidStateTransition(
                    from = state::class.simpleName ?: "unknown",
                    to = "InProgress",
                ).left()
        } else {
            state = RideState.InProgress
            this.right()
        }

    fun complete(): Either<DomainError, Ride> =
        if (state != RideState.InProgress) {
            DomainError
                .InvalidStateTransition(
                    from = state::class.simpleName ?: "unknown",
                    to = "Completed",
                ).left()
        } else {
            state = RideState.Completed
            this.right()
        }

    fun cancel(reason: String): Either<DomainError, Ride> =
        if (state is RideState.Completed) {
            DomainError
                .InvalidStateTransition(
                    from = "Completed",
                    to = "Cancelled",
                ).left()
        } else {
            state = RideState.Cancelled(reason)
            this.right()
        }

    companion object {
        fun create(
            passengerId: UUID,
            pickup: Location,
            destination: Location,
        ): Either<DomainError, Ride> {
            if (pickup == destination) {
                return DomainError
                    .Validation(
                        message = "Pickup and destination must be different",
                    ).left()
            }

            return Ride(
                id = UUID.randomUUID(),
                passengerId = passengerId,
                pickup = pickup,
                destination = destination,
                state = RideState.Requested,
            ).right()
        }
    }
}

data class Location(
    val lat: Double,
    val lon: Double,
)

sealed class RideState {
    data object Requested : RideState()

    data object Accepted : RideState()

    data object InProgress : RideState()

    data object Completed : RideState()

    data class Cancelled(
        val reason: String,
    ) : RideState()
}
