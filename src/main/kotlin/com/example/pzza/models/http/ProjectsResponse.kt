package com.example.pzza.models.http

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class ProjectsResponse(
    @field:NotNull
    @JsonProperty("project_id")
    var projectId:Int?=null,

    @field:NotNull
    @JsonProperty("project_status")
    var projectStatus:String?=null,

    @field:NotNull
    @JsonProperty("project_name")
    var projectName:String?=null,

    @field:NotNull
    @JsonProperty("project_desc")
    var projectDesc:String?=null,

    @field:NotNull
    @JsonProperty("team_members")
    var teamMembers:MutableList<TeamMembers>?= mutableListOf()
)