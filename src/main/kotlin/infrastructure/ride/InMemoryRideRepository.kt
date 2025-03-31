package io.infrastructure.ride

import io.domain.ride.Ride
import io.domain.ride.RideRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.*

class InMemoryRideRepository : RideRepository {
    private val rides = mutableMapOf<UUID, Ride>()
    private val mutex = Mutex()

    override suspend fun save(ride: Ride) {
        mutex.withLock {
            rides[ride.id] = ride
        }
    }

    override suspend fun findById(id: UUID): Ride? =
        mutex.withLock {
            rides[id]
        }
}
