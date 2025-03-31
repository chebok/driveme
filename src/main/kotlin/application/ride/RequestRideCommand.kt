package io.application.ride

import io.domain.ride.Location
import java.util.*

data class RequestRideCommand(
    val passengerId: UUID,
    val pickup: Location,
    val destination: Location,
)
