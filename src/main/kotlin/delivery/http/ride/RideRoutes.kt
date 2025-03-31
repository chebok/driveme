package io.delivery.http.ride

import io.application.ride.CancelRideUseCase
import io.application.ride.CompleteRideUseCase
import io.application.ride.RequestRideUseCase
import io.application.ride.StartRideUseCase
import io.delivery.http.ride.dto.CancelRideDto
import io.delivery.http.ride.dto.RideRequestDto
import io.delivery.http.ride.mapper.toCommand
import io.delivery.http.ride.mapper.toDto
import io.infrastructure.http.respondError
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.rideRoutes() {
    val requestRideUseCase: RequestRideUseCase by inject()
    val cancelRideUseCase: CancelRideUseCase by inject()
    val startRideUseCase: StartRideUseCase by inject()
    val completeRideUseCase: CompleteRideUseCase by inject()

    route("/rides") {
        post("/request") {
            val dto = call.receive<RideRequestDto>()
            val result = requestRideUseCase.execute(dto.toCommand())

            result.fold(
                { error -> call.respondError(error) },
                { ride -> call.respond(HttpStatusCode.Created, ride.toDto()) },
            )
        }

        post("/{id}/cancel") {
            val id = UUID.fromString(call.parameters["id"])
            val dto = call.receive<CancelRideDto>() // если есть причина
            val result = cancelRideUseCase.execute(id, dto.reason)

            result.fold(
                { error -> call.respondError(error) },
                { ride -> call.respond(HttpStatusCode.OK, ride.toDto()) },
            )
        }

        post("/{id}/start") {
            val id = UUID.fromString(call.parameters["id"])
            val result = startRideUseCase.execute(id)

            result.fold(
                { error -> call.respondError(error) },
                { ride -> call.respond(HttpStatusCode.OK, ride.toDto()) },
            )
        }

        post("/{id}/complete") {
            val id = UUID.fromString(call.parameters["id"])
            val result = completeRideUseCase.execute(id)

            result.fold(
                { error -> call.respondError(error) },
                { ride -> call.respond(HttpStatusCode.OK, ride.toDto()) },
            )
        }
    }
}
