package com.example.imusic.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imusic.model.Result
import com.example.imusic.model.Song
import com.example.imusic.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository:Repository) : ViewModel() {

    val myResponse: MutableLiveData<Result> = MutableLiveData()

    fun getSongs(artistName: String) {
        viewModelScope.launch {
            val response = repository.getSongs(artistName)
            myResponse.value = response
        }
    }
}