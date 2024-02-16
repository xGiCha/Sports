package com.sport.kaisbet.domain.models


data class SportUi(
    val sportName: String,
    var eventList: List<Event>,
    val sportId: String,
    var isCollapsed : Boolean = false,
    var switchState: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if(javaClass != other?.javaClass)
            return false

        other as SportUi

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