package com.erenkazancioglu.patikadevweek4.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class Data(
    @SerializedName("_id")
    var id: String?= null,
    @SerializedName("description")
    var description : String? = null,
    var completed : Boolean = false,
    @SerializedName("createdAt")
    var createdAt : Date? =null

)

