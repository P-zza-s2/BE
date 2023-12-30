package com.example.pzza.database

import com.example.pzza.models.ReadApplicantDTO
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="applicant")
class Applicant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "no")
    var no:Int?=null

    @OneToOne
    @JoinColumn(name = "user_no", nullable = false)
    var user:User?=null

    @OneToOne
    @JoinColumn(name = "project_no", nullable = false)
    var project:Project?=null

    @Column(name = "intro", length = 30)
    var intro:String?= null

    @Column(name = "applicant_status")
    var applicantStatus:String?=null

    @Column(name = "created_at")
    var createdAt:String?=null

    @Column(name = "modified_at")
    var modifiedAt:String?=null

    fun toReadApplicantDTO(): ReadApplicantDTO {
        return ReadApplicantDTO().apply{
            this.userId = this@Applicant.user?.id.toString()
            this.role = this@Applicant.user?.role
            this.experience = this@Applicant.user?.career
            this.portfolioUrl = this@Applicant.user?.portfolioUrl
            this.comment = this@Applicant.intro
        }
    }
}