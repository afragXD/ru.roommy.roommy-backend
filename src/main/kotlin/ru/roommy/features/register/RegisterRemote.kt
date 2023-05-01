package ru.roommy.features.register

import kotlinx.serialization.Serializable

@Serializable
data class RegisterReceiveRemote(
    val email:String,
    val password:String,
    val username:String,
)


@Serializable
data class RegisterResponseRemote(
    val token:String,
)