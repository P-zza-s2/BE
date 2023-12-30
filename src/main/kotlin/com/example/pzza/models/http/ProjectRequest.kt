package com.example.pzza.models.http

import com.example.pzza.models.*
import com.fasterxml.jackson.annotation.JsonProperty

data class ProjectRequest(


    @JsonProperty("project_status")
    var projectStatus:String?=null,

    @JsonProperty("project_name")
    var projectName:String?=null,

    @JsonProperty("project_desc")
    var projectDesc:String?=null,
    var period:Int?=null,

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
            this.projectStatus = this@ProjectRequest.projectStatus
            this.devRecruitment = devRecruitment
            this.planRecruitment = planRecruitment
            this.designRecruitment = designRecruitment
            this.chatUrl = this@ProjectRequest.chatUrl
        }
    }
}