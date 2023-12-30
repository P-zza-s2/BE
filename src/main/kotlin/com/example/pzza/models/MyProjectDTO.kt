package com.example.pzza.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ReadMyProjectDTO(
    var projectId:Int?=null,
    var projectStatus:String?=null,
    var projectName:String?=null,
    var projectDesc:String?=null,
    var teamMembers: MutableList<MyTeamMembers> = mutableListOf()
)

data class MyTeamMembers(
    var role:String?=null,
    var participants:Int?=null,
)



data class ReadMyProjectCntDTO(
    @JsonProperty("apply_status")
    var applyStatus:String?=null,
    var count:Int? = null
)
