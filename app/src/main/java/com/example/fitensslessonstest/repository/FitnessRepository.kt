package com.example.fitensslessonstest.repository

import com.example.fitensslessonstest.network.RetrofitInstance

class FitnessRepository {

    suspend fun getFitnessInformation() = RetrofitInstance.api.getFitnessInformation()
}