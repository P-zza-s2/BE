package com.example.pzza.models

import com.example.pzza.database.Applicant
import com.example.pzza.database.Project
import com.example.pzza.database.User
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import java.sql.ClientInfoStatus
@JsonNaming(SnakeCaseStrategy::class)
data class ReadApplicantDTO (
    var userId: String?=null,
    var role: String?=null,
    var experience:Int?=null,
    var portfolioUrl:String?=null,
    var comment:String?=null
)


data class CreateApplicantDTO(
    var project:CreateProjectDTO?=null,
    var user:CreateUserDTO?=null,
    var intro:String?=null,
    var applicantStatus:String?=null,
    var createdAt:String?=null,
    var modifiedAt:String?=null
){
    fun toEntity(): Applicant {
        return Applicant().apply {
            this.project = this@CreateApplicantDTO.project?.toEntity()
            this.user = this@CreateApplicantDTO.user?.toEntity()
            this.intro = this@CreateApplicantDTO.intro
            this.applicantStatus = this@CreateApplicantDTO.applicantStatus
            this.createdAt = this@CreateApplicantDTO.createdAt
            this.modifiedAt = this@CreateApplicantDTO.modifiedAt
        }

    }
}


data class AlterApplicantDTO(
    @JsonProperty("applicant_id")
    var id:Int,
    var status:String,
)

