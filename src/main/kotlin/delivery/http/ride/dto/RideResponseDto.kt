package io.delivery.http.ride.dto

import kotlinx.serialization.Serializable

@Serializable
data class RideResponseDto(
    val id: String,
    val passengerId: String,
    val pickup: LocationDto,
    val destination: LocationDto,
    val state: String,
)
