package com.ort.edu.parcialtp3.services
import retrofit2.Call
import retrofit2.http.GET
import com.ort.edu.parcialtp3.model.Character

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<Character>
}

