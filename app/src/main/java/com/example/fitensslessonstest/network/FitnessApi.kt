package com.example.fitensslessonstest.network

import com.example.fitensslessonstest.models.FitnessResponse
import retrofit2.Response
import retrofit2.http.GET


interface FitnessApi {

    @GET("schedule/get_v3/?club_id=2")
    suspend fun getFitnessInformation():Response<FitnessResponse>
}