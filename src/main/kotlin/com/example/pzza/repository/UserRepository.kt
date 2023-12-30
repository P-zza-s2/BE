package com.example.pzza.repository

import com.example.pzza.database.Skill
import com.example.pzza.database.User
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface UserRepository : CrudRepository<User, Int>{
    fun findAllBy():List<User>

    fun findUserByIdEquals(token:UUID):User?
}

interface SkillsRepository:CrudRepository<Skill,Int>