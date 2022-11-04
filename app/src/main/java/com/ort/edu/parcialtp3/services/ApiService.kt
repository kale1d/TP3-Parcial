package com.ort.edu.parcialtp3.services

import com.ort.edu.parcialtp3.model.CharacterData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<CharacterData>

    @GET("character")
    fun getCharactersByName(@Query("name") name: String): Call<CharacterData>


}

