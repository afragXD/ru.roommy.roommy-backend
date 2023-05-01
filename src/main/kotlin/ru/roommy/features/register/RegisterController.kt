package ru.roommy.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.roommy.database.tokens.TokenDTO
import ru.roommy.database.tokens.Tokens
import ru.roommy.database.user.UserDTO
import ru.roommy.database.user.Users
import ru.roommy.utils.isValidEmail
import java.util.*

class RegisterController(val call: ApplicationCall) {

    suspend fun registerNewUser(){

        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()

        val userDTO = Users.fetchUser(registerReceiveRemote.email)

        if (!registerReceiveRemote.email.isValidEmail()){
            call.respond(HttpStatusCode.InternalServerError, "Email не валидный")
        }else if (userDTO != null){
            call.respond(HttpStatusCode.Conflict, "Пользователь уже существует")
        } else{
            val token = UUID.randomUUID().toString()

            try{
                Users.insert(
                    UserDTO(
                        id = null,
                        email = registerReceiveRemote.email,
                        password = registerReceiveRemote.password,
                        username = registerReceiveRemote.username,
                        gender = true,
                    )
                )
            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.Conflict, "Пользователь уже существует")
            }

            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(), email = registerReceiveRemote.email,
                    token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }

    }
}