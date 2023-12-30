package com.example.pzza.database

import com.example.pzza.models.CreateUserDTO
import com.example.pzza.models.ReadUserDTO
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    var no: Int?=null

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    var id: UUID? = null

    @Column(name = "role", length = 10)
    var role: String? = null

    @Column(name = "career")
    var career: Int? = null

    @Column(name = "portfolio_url", length = 2048)
    var portfolioUrl: String? = null

    @Column(name = "created_at", nullable = false)
    var createdAt: String? = null

    fun Foo(
        id:UUID?=null,
        role:String?=null,
        career:Int?=null,
        portfolioUrl:String?=null,
        createdAt:String?=null
    ): User {
        return this.apply {
            this.id = id
            this.role = role
            this.career = career
            this.portfolioUrl = portfolioUrl
            this.createdAt = createdAt
        }
    }

    fun toReadUserDto(): ReadUserDTO {
        return ReadUserDTO().apply {
            this.no = this@User.no
            this.id = this@User.id
            this.role = this@User.role
            this.career = this@User.career
            this.portfolioUrl = this@User.portfolioUrl
        }
    }

    fun toCreateUserDto(): CreateUserDTO {
        return CreateUserDTO().apply {
            this.id = this@User.id
            this.role = this@User.role
            this.career = this@User.career
            this.portfolioUrl = this@User.portfolioUrl
            this.createdAt=this@User.createdAt
        }
    }
}