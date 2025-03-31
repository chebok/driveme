package io.domain.ride

import java.util.*

interface RideRepository {
    suspend fun save(ride: Ride)

    suspend fun findById(id: UUID): Ride?
}
