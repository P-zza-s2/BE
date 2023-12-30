package com.example.pzza.models

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UserModelTest {

    @Test
    fun `mapping 테스트`(){
        var a = LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))


        println(a.javaClass)
        println(a.javaClass)
        println(a)
        println(a)
        println(a)
        println(a)
    }
}