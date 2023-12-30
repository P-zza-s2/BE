package com.example.pzza.models.http

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ErrorResponse(
    @JsonProperty(value = "result_code")
    var resultCode:String?=null,
    @JsonProperty(value = "http_status")
    var httpStatus:String?=null,
    var method:String?=null,
    var message:String?=null,
    var path:String?=null,
    var timestamp: LocalDateTime?=null,
    var errors:MutableList<_Error> = mutableListOf()
)

data class _Error(
    var field:String?=null,
    var message:String?=null,
    @JsonProperty(value = "request_value")
    var requestValue:Any?=null
)