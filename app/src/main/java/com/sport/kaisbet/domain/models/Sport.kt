package com.sport.kaisbet.domain.models


data class Sport(
    val sportName: String,
    var eventList: List<Event>,
    val sportId: String,
    var isCollapsed : Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass)
            return false

        other as Sport

        return when {
            sportName != other.sportName -> false
            sportId != other.sportId -> false
            isCollapsed != other.isCollapsed -> false
            eventList != other.eventList -> false
            else -> true
        }

    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}