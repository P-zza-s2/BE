package com.example.pzza.models

import com.example.pzza.database.Skill
import com.example.pzza.database.User

//data class ReadSkilDTO(
//    var user: User? = null,
//    var name: String? = null
//)

data class CreateSkilDTO(
    var user: User? = null,
    var name: String? = null
){
    fun toEntity(): Skill {
        return Skill().Foo(
            user = this.user,
            name = this.name
        )
    }
}