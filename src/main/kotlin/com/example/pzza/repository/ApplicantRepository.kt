package com.example.pzza.repository

import com.example.pzza.database.Applicant
import com.example.pzza.database.Project
import com.example.pzza.database.User
import com.example.pzza.models.ReadApplicantDTO
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ApplicantRepository  : CrudRepository<Applicant, Int>  {

    fun findAllByUserAndProjectAndApplicantStatus(
        user: User?,
        project:Project,
        applicantStatus:String
    ): List<Applicant>


    @Transactional
    @Modifying
    @Query("UPDATE Applicant a SET a.applicantStatus = :newStatus, a.modifiedAt = :modifiedAt WHERE a.no = :applicantId")
    fun updateApplicantByNo(applicantId: Int, newStatus: String?, modifiedAt:String)

    @Query(
        "SELECT a.applicantStatus, COUNT(a.applicantStatus) AS applicantStatusCount " +
                "FROM Applicant a " +
                "WHERE a.user.no = :userId " +
                "GROUP BY a.applicantStatus"
    )
    fun findApplicationContByUserNo(@Param("userId") userId: Int): List<Array<String>>
}
