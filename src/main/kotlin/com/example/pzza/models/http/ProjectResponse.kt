package com.example.pzza.models.http

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.UUID

data class ProjectResponse(
    var writer:UUID?=null,

    @JsonProperty("project_id")
    var projectId:Int?=null,

    @JsonProperty("project_status")
    var projectStatus:String?=null,

    @JsonProperty("project_name")
    var projectName:String?=null,

    @JsonProperty("project_desc")
    var projectDesc:String?=null,

    @JsonProperty("team_members")
    var teamMembers:MutableList<TeamMembers>?= mutableListOf()
)

data class TeamMembers(
    var role:String?=null,
    var participants:Int?=null,

    @JsonProperty("recruitment_target")
    var recruitmentTarget:Int?=null,
)
