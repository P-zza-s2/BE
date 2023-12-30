package com.example.pzza.models
import com.example.pzza.database.Project
import com.example.pzza.database.User
import com.example.pzza.models.http.ProjectResponse
import com.example.pzza.models.http.ProjectsResponse
import com.example.pzza.models.http.TeamMembers

data class ReadProjectDTO(
    var writer:ReadUserDTO? = null,
    var no: Int?=null,
    var title: String?=null,
    var content: String?=null,
    var period: Int?=null,
    var devRecruitment: Int?=null,
    var planRecruitment: Int?=null,
    var designRecruitment: Int?=null,
    var projectStatus: String?=null,
    var chatUrl: String?=null,
    var createdAt: String?=null
) {
    fun toProjectResponse(): ProjectsResponse {
        var teamMembers:MutableList<TeamMembers> = mutableListOf(
            TeamMembers().apply {
                role = "개발자"
                participants = 0
                recruitmentTarget = devRecruitment
            },

            TeamMembers().apply {
                role = "기획"
                participants = 0
                recruitmentTarget = planRecruitment
            },

            TeamMembers().apply {
                role = "디자이너"
                participants = 0
                recruitmentTarget = designRecruitment
            }
        )

        return ProjectsResponse().apply {
            this.projectId = this@ReadProjectDTO.no
            this.projectStatus = this@ReadProjectDTO.projectStatus
            this.projectName = this@ReadProjectDTO.title
            this.projectDesc = this@ReadProjectDTO.content
            this.teamMembers = teamMembers
        }
    }
}

data class CreateProjectDTO(
    var writer: User? = null,
    var title: String?=null,
    var content: String?=null,
    var period: Int?=null,
    var devRecruitment: Int?=null,
    var planRecruitment: Int?=null,
    var designRecruitment: Int?=null,
    var chatUrl: String?=null,
    var createdAt: String?=null,
    var projectStatus: String?=null,
    var guideLine: String?=null
) {
    fun toEntity(): Project {
        return Project().Foo().apply {
            this.writer = this@CreateProjectDTO.writer
            this.title = this@CreateProjectDTO.title
            this.content = this@CreateProjectDTO.content
            this.period = this@CreateProjectDTO.period
            this.devRecruitment = this@CreateProjectDTO.devRecruitment
            this.planRecruitment = this@CreateProjectDTO.planRecruitment
            this.designRecruitment = this@CreateProjectDTO.designRecruitment
            this.chatUrl = this@CreateProjectDTO.chatUrl
            this.createdAt = this@CreateProjectDTO.createdAt
            this.projectStatus = this@CreateProjectDTO.projectStatus
            this.guideLine = this@CreateProjectDTO.guideLine
        }
    }
}