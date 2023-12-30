package com.example.pzza.repository

import com.example.pzza.database.Project
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ProjectRepository  : CrudRepository<Project, Int>  {
    fun findAllBy(pageable: Pageable): List<Project>

    fun findProjectByNo(int: Int): Project

    @Transactional
    @Modifying
    @Query("UPDATE Project p SET p.projectStatus = :newStatus WHERE p.no = :projectId")
    fun updateProjectByNo(projectId: Int, newStatus: String?)
}
