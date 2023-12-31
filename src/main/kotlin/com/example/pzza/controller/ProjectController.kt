package com.example.pzza.controller

import com.example.pzza.models.http.ProjectRequest
import com.example.pzza.models.http.ProjectsResponse
import com.example.pzza.service.ProjectsService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/project")
class ProjectController {

    @Autowired
    private lateinit var projectsService: ProjectsService

    @GetMapping(path = ["/{id}"])
    fun projectController(
        @RequestHeader(name = "ACCESS_KEY")
        token:String,

        @PathVariable
        id:Int

    ): ResponseEntity<ProjectsResponse> {
        var projectResponse = projectsService.getProject(id)

        return ResponseEntity.ok().body(
            projectResponse
        )
    }

    @PostMapping
    fun projectPostController(
        @RequestHeader(name = "ACCESS_KEY")
        token:String,

        @Valid
        @RequestBody
        projectRequest: ProjectRequest
    ): ResponseEntity<String> {
        projectsService.saveProject(projectRequest, token).run {
            return ResponseEntity.ok().body(
                "프로젝트가 정상 등록되었습니다"
            )
        }
    }
}