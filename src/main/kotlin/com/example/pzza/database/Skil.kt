package com.example.pzza.database

import com.example.pzza.models.CreateSkilDTO
import jakarta.persistence.*

@Entity
@Table(name = "skil")
class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "no")
    var no: Int? = null

    @ManyToOne
    @JoinColumn(name = "user_no", nullable = false)
    var user: User? = null

    @Column(name = "name", length = 20, nullable = false)
    var name: String? = null

    fun Foo(
        user: User?=null,
        name:String?=null
        ):Skill{
        return this.apply {
            this@Skill.user = user
            this@Skill.name = name
        }
    }

//    fun toReadSkilDTO(): ReadSkilDTO{
//        return ReadSkilDTO().apply {
//            this.user = this@Skil.user
//            this.name = this@Skil.name
//        }
//    }

    fun toCreateSkilDTO():CreateSkilDTO{
        return  CreateSkilDTO().apply {
            this.user = this@Skill.user
            this.name = this@Skill.name
        }
    }
}