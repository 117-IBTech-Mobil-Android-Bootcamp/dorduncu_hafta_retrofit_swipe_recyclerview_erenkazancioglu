package com.erenkazancioglu.patikadevweek4.model.response

import com.erenkazancioglu.patikadevweek4.model.Data
import com.google.gson.annotations.SerializedName

class TasksResponse (
    @SerializedName("data") val dataList: ArrayList<Data>,
    val count : Int
    )