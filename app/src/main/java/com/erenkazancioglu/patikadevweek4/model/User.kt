package com.erenkazancioglu.patikadevweek4.model

import com.google.gson.annotations.SerializedName

class User()
{
    @SerializedName("name")
    var name: String?= null
    var age : Int ?= null
    var email : String ?= null
    var token : String ?= null

    companion object{
        var user : User ?= null
        fun getCurrentInstance() : User {
            if(user == null)
                user = User()
            return user!!
        }
    }

    fun setUser(LoggedInUser: User){
        user?.age = LoggedInUser.age
        user?.name = LoggedInUser.name
        user?.email = LoggedInUser.email
    }
}