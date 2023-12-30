package com.example.pzza.service

import com.example.pzza.enum.MyProjectStatus
import com.example.pzza.models.ReadMyProjectCntDTO
import com.example.pzza.models.ReadMyProjectDTO
import com.example.pzza.repository.ApplicantRepository
import com.example.pzza.repository.MyProjectRepository
import com.example.pzza.repository.UserRepository
import org.hibernate.validator.internal.util.stereotypes.Immutable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class MyProjectService {

    @Autowired
    lateinit var myProjectRepository: MyProjectRepository

    @Autowired
    lateinit var userRepository: UserRepository


    fun getMyProjectCount(token:String): MutableList<ReadMyProjectCntDTO> {
        var binaryToken = UUID.fromString(token)
        val user  = userRepository.findUserByIdEquals(binaryToken)
        val myProjectStatusCounts = user?.no?.let { myProjectRepository.findProjectStatusCountsByUser(it) }

        val myProjectsStatus: MutableMap<String, ReadMyProjectCntDTO> = mutableMapOf(
            MyProjectStatus.진행.toString() to ReadMyProjectCntDTO(MyProjectStatus.진행.toString(), 0),
            MyProjectStatus.완료.toString() to ReadMyProjectCntDTO(MyProjectStatus.완료.toString(), 0),
            MyProjectStatus.거절.toString() to ReadMyProjectCntDTO(MyProjectStatus.거절.toString(), 0),
            MyProjectStatus.심사.toString() to ReadMyProjectCntDTO(MyProjectStatus.심사.toString(), 0),
            MyProjectStatus.중단.toString() to ReadMyProjectCntDTO(MyProjectStatus.중단.toString(), 0)
        )

        myProjectStatusCounts?.forEach { it ->
            val status = it?.get(0)
            val cnt = it?.get(1)?.toInt()

            myProjectsStatus[status]?.count = cnt ?: 0
        }.run {
            return myProjectsStatus.values.toMutableList()
        }
    }

    fun getByStatus(projectStatus:String, token:String): List<ReadMyProjectDTO>? {
        var binaryToken = UUID.fromString(token)
        val user  = userRepository.findUserByIdEquals(binaryToken)

        val status = when{
            projectStatus=="in-progress" -> "진행"
            projectStatus=="under-review" -> "심사"
            projectStatus=="completion" -> "완료"
            projectStatus=="rejection" -> "거절"
            else -> "중단"
        }

        return user?.no?.let {
            myProjectRepository.findMyProjectByUserIdAndProjectStatusStatus(
                it, status
            )
        }?.map { it.toReadMyProjectDTO() }
        // DTO만들기
    }



}