package com.example.pzza.models.http

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID
import kotlin.math.min

data class ProjectResponse(
    var writer:UUID?=null,

    @field:NotNull
    @JsonProperty("project_id")
    var projectId:Int?=null,

    @field:NotNull
    @field:NotBlank
    @JsonProperty("project_status")
    var projectStatus:String?=null,

    @field:NotNull
    @field:NotBlank
    @JsonProperty("project_name")
    var projectName:String?=null,

    @field:NotNull
    @field:NotBlank
    @JsonProperty("project_desc")
    var projectDesc:String?=null,

    @field:NotNull
    @JsonProperty("team_members")
    var teamMembers:MutableList<@Valid TeamMembers>?= mutableListOf()
)

data class TeamMembers(
    @field:NotBlank
    @field:NotNull
    var role:String?=null,

    @Min(value = 0)
    var participants:Int?=null,

    @Min(value = 0)
    @JsonProperty("recruitment_target")
    var recruitmentTarget:Int?=null,
)
