package io.delivery.http.ride.mapper

import io.application.ride.RequestRideCommand
import io.delivery.http.ride.dto.LocationDto
import io.delivery.http.ride.dto.RideRequestDto
import io.delivery.http.ride.dto.RideResponseDto
import io.domain.ride.Location
import io.domain.ride.Ride
import java.util.*

fun RideRequestDto.toCommand(): RequestRideCommand =
    RequestRideCommand(
        passengerId = UUID.fromString(passengerId),
        pickup = pickup.toDomain(),
        destination = destination.toDomain(),
    )

fun LocationDto.toDomain() = Location(lat, lon)

fun Ride.toDto(): RideResponseDto =
    RideResponseDto(
        id = id.toString(),
        passengerId = passengerId.toString(),
        pickup = pickup.toDto(),
        destination = destination.toDto(),
        state = currentState::class.simpleName ?: "unknown",
    )

fun Location.toDto() = LocationDto(lat, lon)
