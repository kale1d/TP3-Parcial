package com.ort.edu.parcialtp3.services
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFagctory

object ApiServiceBuilder {

    private val BASE_URL = "https://rickandmortyapi.com/api/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun create(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}