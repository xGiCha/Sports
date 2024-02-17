package com.sport.kaisbet.common

import com.sport.kaisbet.database.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi

object DummyData {

    val dummyEvent = Event(
        eventName = "Millwall FC U21",
        eventId = "40283433",
        eventSubName = "AFC Bournemouth U21",
        eventStartTime = 1703364840,
        sportId = "FOOT",
        hasFavorite = false
    )

    val dummyEventFavorite = Event(
        eventName = "Millwall FC U21",
        eventId = "40283433",
        eventSubName = "AFC Bournemouth U21",
        eventStartTime = 1703364840,
        sportId = "FOOT",
        hasFavorite = true
    )

    val dummyEventExtra = Event(
        eventName = "Swansea City U21",
        eventId = "40283419",
        eventSubName = "Charlton U21",
        eventStartTime = 1701991440,
        sportId = "FOOT"
    )

    val dummyEventExtraFavorite = Event(
        eventName = "Swansea City U21",
        eventId = "40283419",
        eventSubName = "Charlton U21",
        eventStartTime = 1701991440,
        sportId = "FOOT",
        hasFavorite = true
    )

    val dummySportUi = SportUi(
        sportId = "FOOT",
        sportName = "SOCCER",
        isCollapsed = false,
        switchState = false,
        eventList = listOf(dummyEvent, dummyEventExtra)
    )

    val dummySportUiSwitchState = SportUi(
        sportId = "FOOT",
        sportName = "SOCCER",
        isCollapsed = false,
        switchState = true,
        eventList = listOf(dummyEventFavorite, dummyEventExtraFavorite)
    )

    val dummySportUiFavorite = SportUi(
        sportId = "FOOT",
        sportName = "SOCCER",
        isCollapsed = false,
        switchState = false,
        eventList = listOf(dummyEventFavorite, dummyEventExtra)
    )

    val dummySportsEntity = SportsEntity(
        id = "FOOT",
        sportsUi = dummySportUi
    )


}