package com.example.pzza.models.http

import com.fasterxml.jackson.annotation.JsonProperty

data class ModifiedProjectRequest(
    @JsonProperty("project_id")
    var projectId:Int,

    @JsonProperty("project_status")
    var projectStatus:String?=null,
)