package ru.roommy.database.user

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table() {
    private val id = Users.integer("id")
    private val password = Users.varchar("password", 25)
    private val username = Users.varchar("username", 30)
    private val email = Users.varchar("email", 25)
    private val gender = Users.bool("gender")

    fun insert(userDTO: UserDTO){
        transaction {
            Users.insert {
                it[email] = userDTO.email
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[gender] = userDTO.gender
            }
        }
    }

    fun fetchUser(email:String):UserDTO?{
        return try {
            transaction {
                val userModel = Users.select { Users.email.eq(email) }.single()
                UserDTO(
                    id = userModel[Users.id],
                    email = userModel[Users.email],
                    password = userModel[password],
                    username = userModel[username],
                    gender = userModel[gender],
                )
            }
        } catch (e: Exception){
            null
        }
    }
}