package com.example.pzza.models.http

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SignupRequest (
    @field:NotNull
    @field:NotBlank
    var role: String?=null,

    @field:NotNull
    var experience: Int?=null,

    @field:Size(min=0,max = 2048)
    @JsonProperty("portfolio_url")
    var portfolioUrl: String? = null,

    @field:NotNull
    var skills: MutableList<@Valid @NotBlank String>? = mutableListOf()
)
