package com.example.retrofit.network

import com.example.retrofit.models.MyPostRequest
import com.example.retrofit.models.MyTodo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("rejalar")
    fun getAllTodo(): Call<List<MyTodo>>

    @POST("rejalar/")
    fun addTodo(@Body myPostRequest: MyPostRequest):Call<MyTodo>

    @DELETE("rejalar/{id}/")
    fun deleteTodo(@Path("id") id:Int):Call<Any>

    @PUT("rejalar/{id}/")
    fun updateTodo(@Path("id") id:Int, @Body myPostRequest: MyPostRequest):Call<MyTodo>
}