package com.sport.kaisbet.domain.entities


import com.google.gson.annotations.SerializedName

data class EventEntity(
    @SerializedName("d")
    val d: String,
    @SerializedName("i")
    val i: String,
    @SerializedName("sh")
    val sh: String,
    @SerializedName("si")
    val si: String,
    @SerializedName("tt")
    val tt: Int
)