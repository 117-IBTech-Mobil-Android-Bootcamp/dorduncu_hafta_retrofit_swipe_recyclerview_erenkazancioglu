package com.erenkazancioglu.patikadevweek4.service.api

import com.erenkazancioglu.patikadevweek4.model.response.LoginResponse
import com.erenkazancioglu.patikadevweek4.model.User
import com.erenkazancioglu.patikadevweek4.model.response.TaskResponse
import com.erenkazancioglu.patikadevweek4.model.response.TasksResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TodoAPI {
    @POST("user/login")
    fun login(@Body params : MutableMap<String, Any>) : Call<LoginResponse>

    @GET("user/me")
    fun getLoggedInUser() : Call<User>

    @GET("task")
    fun getAllTask() : Call<TasksResponse>

    @PUT("task/{id}")
    fun updateTaskById(@Path("id") id : String,@Body params : MutableMap<String, Any>) : Call<TaskResponse>

    @POST("task")
    fun addTask(@Body params : MutableMap<String, Any>) : Call<TaskResponse>

    @GET("task")
    fun getTaskWithPagination(@Query("limit") limit : Int,@Query("skip") skip :Int)  : Call<TasksResponse>

    @DELETE("task/{id}")
    fun deleteTaskById(@Path("id") id : String) : Call<Unit>
}