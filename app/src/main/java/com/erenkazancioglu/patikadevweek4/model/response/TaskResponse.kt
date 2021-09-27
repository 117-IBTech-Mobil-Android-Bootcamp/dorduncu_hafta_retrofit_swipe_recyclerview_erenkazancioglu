package com.erenkazancioglu.patikadevweek4.model.response


import com.erenkazancioglu.patikadevweek4.model.Data
import com.google.gson.annotations.SerializedName

class TaskResponse(
    @SerializedName("data") val data: Data,
    @SerializedName("success") val success : Boolean
)