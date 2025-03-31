package io.application.ride

import arrow.core.Either
import arrow.core.raise.either
import io.domain.ride.Ride
import io.domain.ride.RideRepository
import io.domain.shared.DomainError

class RequestRideUseCase(
    private val rideRepository: RideRepository,
) {
    suspend fun execute(command: RequestRideCommand): Either<DomainError, Ride> =
        either {
            val ride =
                Ride
                    .create(
                        passengerId = command.passengerId,
                        pickup = command.pickup,
                        destination = command.destination,
                    ).bind()

            rideRepository.save(ride)

            ride
        }
}
