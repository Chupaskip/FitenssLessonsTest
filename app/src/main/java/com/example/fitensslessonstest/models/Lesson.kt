package com.example.fitensslessonstest.models


import com.google.gson.annotations.SerializedName

data class Lesson(
    @SerializedName("appointment_id")
    val appointmentId: String,
    @SerializedName("available_slots")
    val availableSlots: Int,
    @SerializedName("client_recorded")
    val clientRecorded: Boolean,
    @SerializedName("coach_id")
    var coachId: String?,
    val color: String,
    val commercial: Boolean,
    val date: String,
    val description: String,
    val endTime: String,
    @SerializedName("is_cancelled")
    val isCancelled: Boolean,
    val name: String,
    val place: String,
    @SerializedName("service_id")
    val serviceId: String,
    val startTime: String,
    val tab: String,
    @SerializedName("tab_id")
    val tabId: Int
)