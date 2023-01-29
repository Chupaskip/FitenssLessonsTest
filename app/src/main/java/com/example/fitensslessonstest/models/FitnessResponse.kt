package com.example.fitensslessonstest.models


data class FitnessResponse(
    var lessons: List<Lesson>,
    val option: Option,
    val tabs: List<Tab>,
    val trainers: List<Trainer>
)