package com.example.pzza.service


import com.example.pzza.database.MyProject
import com.example.pzza.database.Project
import com.example.pzza.database.User
import com.example.pzza.models.*
import com.example.pzza.models.http.ModifiedProjectRequest
import com.example.pzza.models.http.ProjectRequest
import com.example.pzza.models.http.ProjectResponse
import com.example.pzza.models.http.ProjectsResponse
import com.example.pzza.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class ProjectsService {

    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Autowired
    lateinit var projectJPARepository: ProjectJPARepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var applicantRepository: ApplicantRepository

    @Autowired
    lateinit var myProjectRepository: MyProjectRepository

    fun getProjects(offset:Int, status:String): List<ProjectsResponse> {
        val pageSize = 20
        val pageNumber = offset / pageSize - 1

        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)
        val projects = projectRepository.findAllBy(pageable)

        return projects.map{it.toReadDto().toProjectResponse()}
    }

    fun getProject(id:Int): ProjectsResponse {
        return projectRepository.findProjectByNo(id).let {
            println(it.writer)
            println(it.toReadDto())
            it.toReadDto()
        }.toProjectResponse()
    }

    fun saveProject(projectRequest: ProjectRequest, token: String): Project {
        var binaryToken = UUID.fromString(token)

        val writer = userRepository.findUserByIdEquals(binaryToken)

        var createdAt:String = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        var status = "모집중"

        val result = projectRequest.toProjectDTO().apply{
            this.writer = writer
            this.projectStatus = status
            this.createdAt = createdAt
            this.guideLine = when{
                period==10 -> "Guide1"
                else -> "Guide2"
            }
        }.run {
            print(this)
            print(this.toEntity())
            projectRepository.save(this.toEntity())
        }

        return result
    }

    fun updateProject(modifiedProjectRequest: ModifiedProjectRequest){
        return projectRepository.updateProjectByNo(
            modifiedProjectRequest.projectId,
            modifiedProjectRequest.projectStatus).run {
            modifiedProjectRequest.projectStatus
        }
    }

    //지원 신청
    fun saveApplicant(createApplicantDTO: CreateApplicantDTO, id:Int, token: String){
        var binaryToken = UUID.fromString(token)
        val user  = userRepository.findUserByIdEquals(binaryToken)

        val project = projectJPARepository.getOne(id)

        var createdAt:String = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        var modifiedAt:String = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        var status = "대기"

        createApplicantDTO.apply {
            this.createdAt = createdAt
            this.modifiedAt = modifiedAt
            this.applicantStatus = status
        }.toEntity().apply {
            this.project = project
            this.user = user
        }.let {applicant->
            applicantRepository.save(applicant)
            myProjectRepository.save(MyProject().apply {
                this.project = applicant.project
                this.user = applicant.user
                this.projectStatus = "대기"
            })
            //my프로젝트에도 저장
        }
    }


    // 지원자 전체 조회
    fun getApplicants(id: Int, token: String): List<ReadApplicantDTO> {
        var binaryToken = UUID.fromString(token)
        val user: User? = userRepository.findUserByIdEquals(binaryToken)

        val project = projectJPARepository.getOne(id)

        return applicantRepository.findAllByUserAndProjectAndApplicantStatus(
            user, project, "대기"
        ).map {
            it.toReadApplicantDTO()
        }
    }

    fun updateApplicationStatus(
        projectId:Int, alterApplicantDTO: AlterApplicantDTO, token:String
    ): Boolean {
        var binaryToken = UUID.fromString(token)

        // 신원 확인
        val userNo = userRepository.findUserByIdEquals(binaryToken)?.no

        // 프로젝트 주인 확인
        val project = projectJPARepository.getOne(projectId)
        val projectWriteNo = project.writer?.no
        val projectId: Int? = project.no

        if (userNo != projectWriteNo){
            return false
        }

        var modifiedAt:String = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

        applicantRepository.updateApplicantByNo(
            alterApplicantDTO.id,
            alterApplicantDTO.status,
            modifiedAt
        ).run {
            //마이프로젝트 업데이트
            myProjectRepository.updateProjectStatus(
                userNo, projectId,alterApplicantDTO.status
            )

            return true
        }
    }
}