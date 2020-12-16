package com.example.imusic.api

import com.example.imusic.model.Result
import com.example.imusic.model.Song
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {

    @GET("search?")
    suspend fun getSongs(@Query("term") artistName: String): Result
//    @Query("term") artistName: String
}