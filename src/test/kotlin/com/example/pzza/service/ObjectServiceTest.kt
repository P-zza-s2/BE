package com.example.pzza.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment

@SpringBootTest
class ObjectServiceTest {

    @Autowired
    private lateinit var objectStorageServiceImpl: ObjectStorageServiceImpl

    val bucketName = "pzza-bucket"

    @Test
    fun uploadFolder(){
        val result = objectStorageServiceImpl.createFolder(
            bucketName = bucketName,
            folderName = "test"
        )

        Assertions.assertEquals(true, result)
    }

}