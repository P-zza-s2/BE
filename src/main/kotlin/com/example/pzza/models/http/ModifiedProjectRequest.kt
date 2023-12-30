package com.example.pzza.models.http

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull

data class ModifiedProjectRequest(
    @NotNull
    @JsonProperty("project_id")
    var projectId:Int,

    @NotNull
    @JsonProperty("project_status")
    var projectStatus:String?=null,
)