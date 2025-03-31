package io.delivery.http.ride.dto

import kotlinx.serialization.Serializable

@Serializable
data class RideRequestDto(
    val passengerId: String,
    val pickup: LocationDto,
    val destination: LocationDto,
)

@Serializable
data class LocationDto(
    val lat: Double,
    val lon: Double,
)
