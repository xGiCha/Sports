package com.sport.kaisbet.ui.mappers

import com.sport.kaisbet.domain.entities.EventEntity
import com.sport.kaisbet.domain.entities.SportEntity
import com.sport.kaisbet.domain.entities.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.Sport
import com.sport.kaisbet.units.splitEventName
import javax.inject.Inject

class SportsMapper @Inject constructor() : SportsMapperInterface {

    override fun mapSports(sports: SportsEntity): List<Sport> {
        val sportsArrayList = arrayListOf<Sport>()
        sports.forEach { sportEntity ->
            val sport = Sport(
                sportId = sportEntity.i,
                sportName = sportEntity.d,
                eventList = mapEventEntity(sportEntity.e)
            )
            sportsArrayList.add(sport)
        }
        return sportsArrayList
    }

    private fun mapEventEntity(eventList: List<EventEntity>): List<Event> {
        val eventArrayList = arrayListOf<Event>()
        eventList.forEach { eventEntity ->
            val event = Event(
                eventId = eventEntity.i,
                sportId = eventEntity.si,
                eventName = eventEntity.d.splitEventName(0),
                eventSubName = eventEntity.d.splitEventName(1),
                eventStartTime = eventEntity.tt
            )
            eventArrayList.add(event)
        }

        return eventArrayList
    }
}