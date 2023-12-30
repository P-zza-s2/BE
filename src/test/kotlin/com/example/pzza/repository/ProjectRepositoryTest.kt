package com.example.pzza.repository

import com.example.pzza.models.CreateProjectDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@SpringBootTest
class ProjectRepositoryTest {
    @Autowired
    lateinit var projectRepository: ProjectRepository

    @Test
    fun `getProjects 테스트`(){
        val pageable: Pageable = PageRequest.of(0, 20)
        val projects = projectRepository.findAllBy(pageable)

        projects.forEach{
            println(it.toReadDto())
        }
    }

    @Test
    fun `saveProject 테스트`(){

        val project = CreateProjectDTO().apply {
            this.title= "테스트중"
            this.content = "테스트중"
            this.period = 10
            this.devRecruitment = 3
            this.planRecruitment = 3
            this.designRecruitment = 3
            this.chatUrl = "test.com"
            this.createdAt = "2023-12-16 00:00:00"
        }.toEntity().run {
            projectRepository.save(this)
        }

        println(project)

    }


}