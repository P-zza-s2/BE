package com.example.pzza.service

import com.example.pzza.models.CreateSkilDTO
import com.example.pzza.models.CreateUserDTO
import com.example.pzza.models.ReadUserDTO
import com.example.pzza.models.http.SignupRequest
import com.example.pzza.repository.SkillsRepository
import com.example.pzza.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var skillsRepository: SkillsRepository

    fun getUsers(): List<ReadUserDTO>{
        val users = userRepository.findAll()
        return users.map { it.toReadUserDto() }
    }

    fun getUser(token: String): ReadUserDTO? {
        var binaryToken = UUID.fromString(token)
        val user = userRepository.findUserByIdEquals(binaryToken)

        print(user)
        return user?.toReadUserDto()
    }

    fun saveUsers(signupRequest: SignupRequest): UUID? {
        var id:UUID = UUID.randomUUID()
        var createdAt:String = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        var user = userRepository.save(CreateUserDTO().apply {
            this.id = id
            this.createdAt = createdAt
            this.career = signupRequest.experience
            this.role = signupRequest.role
            this.portfolioUrl = signupRequest.portfolioUrl
        }.toEntity())

        var skills: MutableList<CreateSkilDTO> = mutableListOf()
        signupRequest.skills?.forEach{ skill ->
            CreateSkilDTO().apply {
                this.user = user
                this.name = skill
            }.run{
                skills.add(this)
            }
        }

        skillsRepository.saveAll(skills.map{it.toEntity()})

        return id
    }
}