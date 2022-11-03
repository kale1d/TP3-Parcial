package com.ort.edu.parcialtp3.services
import retrofit2.Call
import retrofit2.http.GET
import com.ort.edu.parcialtp3.model.CharacterData

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<CharacterData>
}

