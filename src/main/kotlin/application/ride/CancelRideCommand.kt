package io.application.ride

import java.util.*

data class CancelRideCommand(
    val rideId: UUID,
    val reason: String,
)
