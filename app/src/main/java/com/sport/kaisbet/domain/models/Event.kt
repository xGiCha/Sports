package com.sport.kaisbet.domain.models


import com.google.gson.annotations.SerializedName

data class Event(
    val eventName: String,
    val eventSubName: String,
    val eventId: String,
    val sportId: String,
    var eventStartTime: Int,
    var hasFavorite: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass)
            return false

        other as Event

        return when {
            eventName != other.eventName -> false
            eventSubName != other.eventSubName -> false
            eventId != other.eventId -> false
            sportId != other.sportId -> false
            eventStartTime != other.eventStartTime -> false
            hasFavorite != other.hasFavorite -> false
            else -> true
        }
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}