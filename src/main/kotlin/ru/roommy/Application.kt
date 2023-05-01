package ru.roommy

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import ru.roommy.features.login.configureLoginRouting
import ru.roommy.features.register.configureRegisterRouting
import ru.roommy.plugins.*

fun main() {

    Database.connect("jdbc:postgresql://localhost:5432/roommy", driver = "org.postgresql.Driver",
        user = "postgres", password = "742617"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureLoginRouting()
    configureRegisterRouting()
    configureSerialization()
}
