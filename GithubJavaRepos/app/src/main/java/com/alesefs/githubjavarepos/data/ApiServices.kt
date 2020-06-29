package com.alesefs.githubjavarepos.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServices {
    private const val BASE_URL: String = "https://api.github.com/"
    const val BASE_URL_DISPLAY_NAME =
        "https://api.github.com/users/"

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val services: EndpointServices = initRetrofit().create(
        EndpointServices::class.java)
}