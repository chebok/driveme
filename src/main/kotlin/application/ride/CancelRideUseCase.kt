package io.application.ride

import arrow.core.Either
import arrow.core.raise.either
import io.domain.ride.Ride
import io.domain.ride.RideRepository
import io.domain.shared.DomainError
import java.util.*

class CancelRideUseCase(
    private val rideRepository: RideRepository,
) {
    suspend fun execute(
        id: UUID,
        reason: String,
    ): Either<DomainError, Ride> =
        either {
            val ride =
                rideRepository.findById(id)
                    ?: raise(DomainError.NotFound("Ride", id.toString()))

            val cancelledRide = ride.cancel(reason).bind()

            rideRepository.save(cancelledRide)

            cancelledRide
        }
}
