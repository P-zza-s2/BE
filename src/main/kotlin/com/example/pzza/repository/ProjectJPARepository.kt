package com.example.pzza.repository

import com.example.pzza.database.Project
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface ProjectJPARepository  : JpaRepository<Project,Int>   {

}
