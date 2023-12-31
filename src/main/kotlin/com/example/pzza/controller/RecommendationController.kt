package com.example.pzza.controller

import com.example.pzza.service.ClovarService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/recommendation")
class RecommendationController {

    @Autowired
    lateinit var clovarService: ClovarService

    @PostMapping
    fun getSubjectController(
    ):ResponseEntity<String>{
        return clovarService.getSubject().run{
             ResponseEntity.ok().body(this)
        }


    }

}