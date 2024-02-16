package com.sport.kaisbet.domain.useCases

import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import javax.inject.Inject

class FavoriteHandlerUseCase @Inject constructor() {

    fun addSingleStartToFavorite(
        hasFavorite: Boolean,
        event: Event,
        sportUiList: MutableList<SportUi>
    ): MutableList<SportUi> {
        sportUiList.forEach { sport ->
            sport.eventList.forEachIndexed { indexEvent, eventItem ->
                if (event.eventId != eventItem.eventId) return@forEachIndexed
                sport.eventList = addToFavList(hasFavorite, indexEvent, eventItem, sport.eventList)
            }
            sport.switchState = false
        }
        return sportUiList
    }

    fun addAllStarsToFavorites(
        switchState: Boolean,
        sportUi: SportUi,
        sportUiList: MutableList<SportUi>
    ): MutableList<SportUi> {
        sportUiList.forEachIndexed { index, sport ->
            if (sportUi.sportId != sport.sportId) return@forEachIndexed
            sport.eventList.forEachIndexed { indexEvent, eventItem ->
                sport.eventList = addToFavList(switchState, indexEvent, eventItem, sport.eventList)
            }
            sport.switchState = switchState
        }
        return sportUiList
    }

    private fun addToFavList(
        state: Boolean,
        indexEvent: Int,
        eventItem: Event,
        eventList: List<Event>
    ): MutableList<Event> {
        val newEvent = eventItem.copy()
        newEvent.hasFavorite = state
        val sportEventArrayList = eventList.toMutableList()
        sportEventArrayList[indexEvent] = newEvent
        return sportEventArrayList
    }
}