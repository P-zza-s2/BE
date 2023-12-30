package com.example.pzza.controller

import com.example.pzza.models.AlterApplicantDTO
import com.example.pzza.models.CreateApplicantDTO
import com.example.pzza.models.ReadApplicantDTO
import com.example.pzza.models.http.ModifiedProjectRequest
import com.example.pzza.models.http.ProjectsResponse
import com.example.pzza.service.ProjectsService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/projects")
class ProjectsController {

    @Autowired
    private lateinit var projectsService: ProjectsService

    @GetMapping
    fun allProjectController(
        @RequestHeader(name = "ACCESS_KEY")
        token:String,
        @RequestParam
        @Min(value = 20)
        offset:Int,
        @RequestParam
        status:String
    ): ResponseEntity<List<ProjectsResponse>> {
        val projectsResponse = projectsService.getProjects(offset, status)
        return ResponseEntity.ok().body(projectsResponse)
    }

    @PutMapping
    fun PutProjectController(
        @RequestHeader(name = "ACCESS_KEY")
        token:String,

        @RequestBody
        modifiedProjectRequest: ModifiedProjectRequest
    ): ResponseEntity<String> {
        var status = projectsService.updateProject(modifiedProjectRequest)
        return ResponseEntity.ok().body(
            "프로젝트가 ${status} 되었습니다"
        )
    }

    @PostMapping(path = ["/{id}/applicant"])
    fun applicantPostController(
        @PathVariable
        id:Int,
        @RequestHeader(name = "ACCESS_KEY")
        token:String,
        @RequestBody
        createApplicantDTO: CreateApplicantDTO
    ){
        //SQLIntegrityConstraintViolationException 에러 시 같인곳 지원한거임

        return projectsService.saveApplicant(createApplicantDTO, id, token)
            .run {
            ResponseEntity.ok().body(
                "팀원 신청을 완료했습니다"
            )
        }
    }

    @GetMapping(path = ["/{id}/applicants"])
    fun applicantGetController(
        @PathVariable
        id:Int,
        @RequestHeader(name = "ACCESS_KEY")
        token:String,
    ):ResponseEntity<List<ReadApplicantDTO>>{
        return projectsService.getApplicants(id, token).run {
            ResponseEntity.ok().body(this)
        }
    }

    @PutMapping(path = ["/{id}/applicants"])
    fun applicantPutController(
        @RequestHeader(name = "ACCESS_KEY")
        token:String,
        @PathVariable(name="id")
        projectId:Int,
        @Valid
        @RequestBody
        alterApplicantDTO: AlterApplicantDTO
    ): ResponseEntity<String> {
        val result = projectsService.updateApplicationStatus(projectId, alterApplicantDTO, token)

        return if (result) {
            ResponseEntity.ok().body(
                "팀원 신청을 ${alterApplicantDTO.status} 했습니다"
            )
        } else {
            ResponseEntity.badRequest().body(
                "프로젝트 주인이 아닙니다"
            )
        }
    }
}