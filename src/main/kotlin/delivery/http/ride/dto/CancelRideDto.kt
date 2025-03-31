package io.delivery.http.ride.dto

import kotlinx.serialization.Serializable

@Serializable
data class CancelRideDto(
    val reason: String,
)
