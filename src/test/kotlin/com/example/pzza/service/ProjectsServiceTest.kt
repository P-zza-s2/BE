package com.example.pzza.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProjectsServiceTest {
    @Autowired
    lateinit var projectsService: ProjectsService

    @Test
    fun `getProjects 테스트`(){
        val result = projectsService.getProjects(20, "inprogress")
        result.forEach{
            println(it)
        }

        Assertions.assertEquals(10, result.size)
    }

    @Test
    fun `saveUsers테스트`(){

    }
}