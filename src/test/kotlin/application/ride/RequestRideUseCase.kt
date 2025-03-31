package io.application.ride

import io.domain.ride.*
import io.domain.shared.DomainError
import io.infrastructure.ride.InMemoryRideRepository
import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.*

class RequestRideUseCaseSpec :
    StringSpec({

        val passengerId = UUID.randomUUID()
        val pickup = Location(55.0, 37.0)
        val destination = Location(56.0, 38.0)

        "should create ride and save it to repository" {
            val repository = InMemoryRideRepository()
            val useCase = RequestRideUseCase(repository)

            val command =
                RequestRideCommand(
                    passengerId = passengerId,
                    pickup = pickup,
                    destination = destination,
                )

            val result = useCase.execute(command)

            val ride = result.shouldBeRight()
            ride.passengerId shouldBe passengerId
            ride.pickup shouldBe pickup
            ride.destination shouldBe destination

            repository.findById(ride.id) shouldBe ride
        }

        "should fail with validation error when pickup == destination" {
            val repository = InMemoryRideRepository()
            val useCase = RequestRideUseCase(repository)

            val command =
                RequestRideCommand(
                    passengerId = passengerId,
                    pickup = pickup,
                    destination = pickup,
                )

            val result = useCase.execute(command)

            result.shouldBeLeft().also { error ->
                error shouldBe
                    DomainError.Validation(
                        message = "Pickup and destination must be different",
                    )
            }
        }
    })
