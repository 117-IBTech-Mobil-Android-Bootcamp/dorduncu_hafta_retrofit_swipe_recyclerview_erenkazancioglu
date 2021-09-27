package com.erenkazancioglu.patikadevweek4.model.response

import com.erenkazancioglu.patikadevweek4.model.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user") val user: User,
    val token : String
)