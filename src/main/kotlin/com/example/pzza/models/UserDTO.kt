package com.example.pzza.models

import com.example.pzza.database.User
import java.util.UUID

data class ReadUserDTO(
    var no: Int? = null,
    var id: UUID? = null,
    var role: String? = null,
    var career: Int? = null,
    var portfolioUrl: String? = null
)

data class CreateUserDTO(
    var id: UUID?=null,
    var role: String?=null,
    var career: Int?=null,
    var portfolioUrl: String? = null,
    var createdAt:String?=null
){
    fun toEntity(): User {
        return User().Foo(
            id = id,
            createdAt = createdAt,
            role = role,
            career = career,
            portfolioUrl = portfolioUrl
        )
    }

}

