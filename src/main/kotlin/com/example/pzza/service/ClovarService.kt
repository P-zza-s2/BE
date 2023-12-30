package com.example.pzza.service

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Serializable
data class Keyword(val keyword: String)

@Service
class ClovarService {
    fun getSubject(): String? {
        val clovaUrl = "http://localhost:5000/clova"

        // RestTemplate 객체 생성
        val restTemplate = RestTemplate()

        // HTTP 요청 헤더 설정
        val headers = HttpHeaders()
        headers.setContentType(MediaType.APPLICATION_JSON)

        // 키워드 데이터 생성
        val keywords: List<Keyword> = listOf(
            Keyword("밥"),
            Keyword("공주"),
            Keyword("기타")
        )

        // 데이터를 JSON 형식으로 직렬화
        val jsonData = Json.encodeToString(keywords)

        // HTTP 요청 엔터티 생성
        val requestEntity = HttpEntity(jsonData, headers)

        // HTTP 응답 헤더 설정
        val responseHeaders = HttpHeaders()
        responseHeaders.contentType = MediaType.APPLICATION_JSON_UTF8

        // POST 요청 보내기
        val response = restTemplate.postForEntity(clovaUrl, requestEntity, String::class.java)

        // 한글로 정상 출력
        println(response.body)

        // 응답 출력
        return response.body
    }
}