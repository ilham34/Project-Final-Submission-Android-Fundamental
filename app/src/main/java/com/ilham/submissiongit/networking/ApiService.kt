package com.ilham.submissiongit.networking

import com.ilham.submissiongit.GitResponse
import com.ilham.submissiongit.UserDetail
import com.ilham.submissiongit.UsersItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<GitResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<UserDetail>

    @GET("users/{username}/{tipe}")
    fun getFollow(
        @Path("username") username: String,
            @Path("tipe") tipe: String
    ): Call<ArrayList<UsersItem>>
}