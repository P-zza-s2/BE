package com.example.pzza.models.http

import com.example.pzza.models.*
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kotlin.math.min

data class ProjectRequest(
    @field:Size(min=2, max=20)
    @JsonProperty("project_name")
    var projectName:String?=null,

    @field:Size(min=2, max=500)
    @JsonProperty("project_desc")
    var projectDesc:String?=null,
    var period:Int?=null,

    @field:Size(min=0, max=2048)
    @JsonProperty("chat_url")
    var chatUrl:String?=null,

    @JsonProperty("team_members")
    var teamMembers:MutableList<TeamMembers>?= mutableListOf()
){
    fun toProjectDTO(): CreateProjectDTO {
        var devRecruitment:Int? = 0
        var planRecruitment:Int? = 0
        var designRecruitment: Int? = 0

        this.teamMembers?.forEach{
            if (it.role=="개발자"){
                devRecruitment = it.recruitmentTarget
            }else if (it.role=="기획"){
                planRecruitment = it.recruitmentTarget
            }else{
                designRecruitment = it.recruitmentTarget
            }
        }

        return CreateProjectDTO().apply {
            this.title = this@ProjectRequest.projectName
            this.content = this@ProjectRequest.projectDesc
            this.period = this@ProjectRequest.period
            this.devRecruitment = devRecruitment
            this.planRecruitment = planRecruitment
            this.designRecruitment = designRecruitment
            this.chatUrl = this@ProjectRequest.chatUrl
        }
    }
}