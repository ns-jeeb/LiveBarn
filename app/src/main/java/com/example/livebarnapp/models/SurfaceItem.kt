package com.example.livebarnapp.models

data class SurfaceItem(
    val id: Int,
    val server: Server,
    val sport: String,
    val status: String,
    val surfaceName: String,
    val venueName: String
)