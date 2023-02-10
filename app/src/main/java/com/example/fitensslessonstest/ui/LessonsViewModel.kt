package com.example.fitensslessonstest.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fitensslessonstest.FitnessApplication
import com.example.fitensslessonstest.models.FitnessResponse
import com.example.fitensslessonstest.models.Lesson
import com.example.fitensslessonstest.network.RetrofitInstance
import com.example.fitensslessonstest.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.time.LocalDate

class LessonsViewModel(
    app: Application
) : AndroidViewModel(app) {

    val fitnessInformation: MutableLiveData<Resource<FitnessResponse>> = MutableLiveData()
    var fitnessResponse: FitnessResponse? = null

    init {
        getFitnessInformation()
    }

    private fun getFitnessInformation() = viewModelScope.launch {
        safeFitnessCall()
    }

    private fun handleResponse(response: Response<FitnessResponse>): Resource<FitnessResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                if (fitnessResponse == null) {
                    setFullNamesOfTrainersToLessons(resultResponse)
                    fitnessResponse = resultResponse
                }
                return Resource.Success(fitnessResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun setFullNamesOfTrainersToLessons(fitnessResponse: FitnessResponse) {
        fitnessResponse.lessons = fitnessResponse.lessons.sortedBy { it.date }
        for (lesson in fitnessResponse.lessons) {
            val trainer = fitnessResponse.trainers.find { trainer -> trainer.id == lesson.coachId }
            lesson.coachId = trainer?.fullName
        }
    }

    private suspend fun safeFitnessCall() {
        fitnessInformation.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response: Response<FitnessResponse> =
                    RetrofitInstance.api.getFitnessInformation()
                fitnessInformation.postValue(handleResponse(response))


            } else {
                fitnessInformation.postValue(Resource.Error("Нет интернет соединения"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> fitnessInformation.postValue(Resource.Error("Ошибка сети"))
                else -> fitnessInformation.postValue(Resource.Error("Ошибка конвертирования"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<FitnessApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
        return false
    }

}

