package io.infrastructure.ride

import io.application.ride.*
import io.domain.ride.RideRepository
import org.koin.dsl.module

val rideModule =
    module {
        single<RideRepository> { InMemoryRideRepository() }

        single { RequestRideUseCase(get()) }
        single { CancelRideUseCase(get()) }
        single { StartRideUseCase(get()) }
        single { CompleteRideUseCase(get()) }
    }
