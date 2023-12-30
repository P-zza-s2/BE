package com.example.pzza.repository

import com.example.pzza.database.MyProject
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param


interface MyProjectRepository: CrudRepository<MyProject, Int> {
    @Query(
        "SELECT m.projectStatus, COUNT(m.projectStatus) AS projectCount " +
                "FROM MyProject m " +
                "WHERE m.user.no = :userId " +
                "GROUP BY m.projectStatus"
    )

    fun findProjectStatusCountsByUser(@Param("userId") userId: Int): List<Array<String>>

    @Query(
        "SELECT m AS All " +
                "FROM MyProject m " +
                "WHERE m.user.no = :userId AND m.projectStatus = :projectStatus"
    )
    fun findMyProjectByUserIdAndProjectStatusStatus(
        userId:Int, projectStatus:String
    ):List<MyProject>

    @Transactional
    @Modifying
    @Query(
        "UPDATE MyProject m " +
                "SET m.projectStatus = :projectStatus " +
                "WHERE m.user.no = :userId AND m.project.no = :projectId"
    )
    fun updateProjectStatus(
        @Param("userId") userId: Int?,
        @Param("projectId") projectId: Int?,
        @Param("projectStatus") projectStatus: String?
    ): Int
}