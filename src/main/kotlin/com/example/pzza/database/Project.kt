package com.example.pzza.database

import com.example.pzza.models.CreateProjectDTO
import com.example.pzza.models.CreateUserDTO
import com.example.pzza.models.ReadProjectDTO
import jakarta.persistence.*


@Entity
@Table(name = "project")
class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "no")
    var no:Int?=null

    @ManyToOne
    @JoinColumn(name = "writer", referencedColumnName = "no", nullable = false)
    var writer:User?=null

    @Column(length = 20, nullable = false)
    var title:String?=null

    @Column(length = 500, nullable = false)
    var content:String?=null

    @Column(nullable = false)
    var period:Int?=null

    @Column(name="dev_recruitment")
    var devRecruitment:Int?=null

    @Column(name="plan_recruitment")
    var planRecruitment:Int?=null

    @Column(name="design_recruitment")
    var designRecruitment:Int?=null

    @Column(name="chat_url", length = 2048, nullable = false)
    var chatUrl:String?=null

    @Column(name="created_at")
    var createdAt:String?=null

    @Column(name="guide_line")
    var guideLine:String?= null

    @Column(name="project_status")
    var projectStatus:String?=null

    fun Foo(
        writer:User?=null,
        title:String?=null,
        content:String?=null,
        period:Int?=null,
        devRecruitment:Int?=null,
        planRecruitment:Int?=null,
        designRecruitment:Int?=null,
        chatUrl:String?=null,
        createdAt:String?=null,
        guideLine:String?=null,
        projectStatus:String?=null,
    ):Project{
        this.writer = writer
        this.title = title
        this.content = content
        this.period = period
        this.devRecruitment = devRecruitment
        this.planRecruitment = planRecruitment
        this.designRecruitment = designRecruitment
        this.chatUrl = chatUrl
        this.createdAt = createdAt
        this.projectStatus = projectStatus
        this.guideLine = guideLine

        return this
    }


    fun toReadDto(): ReadProjectDTO {
        return ReadProjectDTO().apply {
            this.no = this@Project.no
            this.writer = this@Project.writer?.toReadUserDto()
            this.title = this@Project.title
            this.content = this@Project.content
            this.period = this@Project.period
            this.devRecruitment = this@Project.devRecruitment
            this.planRecruitment = this@Project.planRecruitment
            this.designRecruitment = this@Project.designRecruitment
            this.projectStatus = this@Project.projectStatus
            this.chatUrl = this@Project.chatUrl
            this.createdAt = this@Project.createdAt
        }
    }
}