package com.example.pzza.database

import com.example.pzza.models.MyTeamMembers
import com.example.pzza.models.ReadApplicantDTO
import com.example.pzza.models.ReadMyProjectCntDTO
import com.example.pzza.models.ReadMyProjectDTO
import com.example.pzza.models.http.TeamMembers
import jakarta.persistence.*

@Entity
@Table(name="my_project")
class MyProject(){
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

    @Column(name = "project_status")
    var projectStatus:String?=null

    @Column(name = "created_at")
    var createdAt:String?=null

    @Column(name = "modified_at")
    var modifiedAt:String?=null

    fun toReadMyProjectDTO(): ReadMyProjectDTO {
        return ReadMyProjectDTO().apply{
            this.projectId = this@MyProject.project?.no
            this.projectName = this@MyProject.project?.title
            this.projectStatus = this@MyProject.projectStatus
            this.projectDesc = this@MyProject.project?.content
            this.teamMembers = mutableListOf(
                MyTeamMembers().apply {
                    this.role = "개발자"
                    this.participants = 0
                },MyTeamMembers().apply {
                    this.role = "기획"
                    this.participants = 0
                },MyTeamMembers().apply {
                    this.role = "디자이너"
                    this.participants = 0
                }
            )
        }
    }
}