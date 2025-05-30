package com.appdev.harvest.models

data class GardenItem(
    val name: String,
    val description: String = "",
    val plantCount: Int = 0
)