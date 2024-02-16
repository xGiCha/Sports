package com.sport.kaisbet.presentation.mappers

import com.sport.kaisbet.domain.entities.EventEntity
import com.sport.kaisbet.domain.entities.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.common.splitEventName
import javax.inject.Inject

class SportsMapper @Inject constructor() : SportsMapperInterface {

    override fun mapSports(sports: SportsEntity): List<SportUi> {
        val sportsArrayList = arrayListOf<SportUi>()
        sports.forEach { sportEntity ->
            val sport = SportUi(
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