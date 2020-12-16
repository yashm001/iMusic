package com.example.imusic.repository

import com.example.imusic.api.RetrofitInstance
import com.example.imusic.model.Result
import retrofit2.Response

class Repository {

    suspend fun getSongs(artistName : String): Result {
        return RetrofitInstance.api.getSongs(artistName)
    }
}