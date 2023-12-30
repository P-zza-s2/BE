package com.example.pzza.controller

import com.example.pzza.models.ReadMyProjectCntDTO
import com.example.pzza.models.ReadMyProjectDTO
import com.example.pzza.service.MyProjectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/my-projects")
class MyProjectController {

    @Autowired
    private lateinit var myProjectService: MyProjectService

    @GetMapping
    fun myProjectGetController(
        @RequestHeader(name = "ACCESS_KEY")
        token:String,
    ):ResponseEntity<List<ReadMyProjectCntDTO>>{
        return myProjectService.getMyProjectCount(token).run {
            ResponseEntity.ok().body(this)
        }
    }

    //내 프로젝트 상태별 조회
    @GetMapping(path = ["/{id}"])
    fun myProjectGETController(
        @RequestHeader(name = "ACCESS_KEY")
        token:String,

        @PathVariable("id")
        projectStatus:String
    ):ResponseEntity<List<ReadMyProjectDTO>>{
        return myProjectService.getByStatus(projectStatus, token).run {
            ResponseEntity.ok().body(this)
        }
    }
}