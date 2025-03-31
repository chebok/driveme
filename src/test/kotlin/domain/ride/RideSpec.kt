package io.domain.ride

import io.kotest.assertions.arrow.core.shouldBeLeft
import io.kotest.assertions.arrow.core.shouldBeRight
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.util.*

class RideSpec :
    StringSpec({

        "should create a Ride with valid input" {
            val passengerId = UUID.randomUUID()
            val pickup = Location(55.0, 37.0)
            val destination = Location(56.0, 38.0)

            val result = Ride.create(passengerId, pickup, destination)

            result.shouldBeRight().apply {
                this.passengerId shouldBe passengerId
                this.pickup shouldBe pickup
                this.destination shouldBe destination
            }
        }

        "should fail when pickup and destination are the same" {
            val passengerId = UUID.randomUUID()
            val location = Location(55.0, 37.0)

            val result = Ride.create(passengerId, location, location)

            result.shouldBeLeft().message shouldBe "Pickup and destination must be different"
        }
    })
