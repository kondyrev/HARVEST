// Zone.kt
package com.appdev.harvest.models

import java.util.*

data class Zone(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val userId: String = "",
    val imageUrl: String = ""
)