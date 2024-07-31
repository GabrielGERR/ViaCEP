package com.example.puxardados

// ViaCepService.kt
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("ws/{cep}/json/")
    fun getAddress(@Path("cep") cep: String): Call<AndressCEP>
}
