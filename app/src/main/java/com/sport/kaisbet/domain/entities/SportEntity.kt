package com.sport.kaisbet.domain.entities


import com.google.gson.annotations.SerializedName

data class SportEntity(
    @SerializedName("d")
    val d: String,
    @SerializedName("e")
    val e: List<EventEntity>,
    @SerializedName("i")
    val i: String
)