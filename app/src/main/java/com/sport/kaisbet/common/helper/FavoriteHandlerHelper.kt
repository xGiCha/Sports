package com.sport.kaisbet.common.helper

import com.sport.kaisbet.data.LocalDataSource
import com.sport.kaisbet.database.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteHandlerHelper @Inject constructor(
    private val localDataSource: LocalDataSource
): FavoriteHandlerHelperInterface {
    override fun addSingleStartToFavorite(
        hasFavorite: Boolean,
        event: Event,
        sportUiList: MutableList<SportUi>
    ): MutableList<SportUi> {
        val list = sportUiList.map { sport ->
            val updatedEvents = updateEventsWithSingleFavorite(hasFavorite, event, sport)
            val updatedSportUi = updateSportUiWithFavorites(false, sport, updatedEvents)
            if(updatedEvents != sport.eventList)
                handleDbItems(updatedSportUi)
            updatedSportUi
        }.toMutableList()

        return list
    }

    override fun addAllStarsToFavorites(
        switchState: Boolean,
        sportUi: SportUi,
        sportUiList: MutableList<SportUi>
    ): MutableList<SportUi> {
        val list = sportUiList.map { sport ->
            val newSportUi = if (sportUi.sportId != sport.sportId) {
                sport
            } else {
                val updatedEvents = updateEventsWithFavorites(switchState, sport.eventList)
                val updatedSportUi = updateSportUiWithFavorites(switchState, sport, updatedEvents)
                handleDbItems(updatedSportUi, switchState)
                updatedSportUi
            }
            newSportUi
        }.toMutableList()
        return list
    }

    private fun updateEventsWithSingleFavorite(
        hasFavorite: Boolean,
        event: Event,
        sport: SportUi
    ): List<Event> {
        return sport.eventList.mapIndexed { index, eventItem ->
            if (event.eventId == eventItem.eventId) {
                updateEventWithFavorite(hasFavorite, eventItem)
            } else {
                eventItem
            }
        }
    }

    private fun updateEventsWithFavorites(
        switchState: Boolean,
        eventList: List<Event>
    ): List<Event> {
        return eventList.map { eventItem ->
            updateEventWithFavorite(switchState, eventItem)
        }
    }

    private fun updateEventWithFavorite(
        switchState: Boolean,
        eventItem: Event
    ): Event {
        return eventItem.copy(hasFavorite = switchState)
    }

    private fun updateSportUiWithFavorites(
        state: Boolean,
        sportUi: SportUi,
        updatedEvents: List<Event>
    ): SportUi {
        return sportUi.copy(switchState = state, eventList = updatedEvents.toMutableList())
    }

    override fun fetchFavoriteFromDb(): Flow<List<SportsEntity>> {
        return localDataSource.getSportsList()
    }

    override fun compareNetworkWithDbFavorite(
        networkList: List<SportUi>,
        dbList: List<SportUi>
    ): List<SportUi> {
        return if (dbList.isNotEmpty()) {
            networkList.map { networkSport ->
                val dbSport = dbList.find { it.sportId == networkSport.sportId }
                mergeSportUi(networkSport, dbSport)
            }
        } else {
            networkList
        }
    }

    private fun mergeSportUi(networkSport: SportUi, dbSport: SportUi?): SportUi {
        val mergedEventList = networkSport.eventList.map { networkEvent ->
            val dbEvent = dbSport?.eventList?.find { it.eventId == networkEvent.eventId }
            dbEvent ?: networkEvent
        }
        val isCollapsed = dbSport?.isCollapsed ?: false
        val switchState = dbSport?.switchState ?: false
        return networkSport.copy(isCollapsed = isCollapsed, switchState = switchState, eventList = mergedEventList)
    }

    private fun handleDbItems(sportUi: SportUi, state: Boolean = true) {
        if (state)
            localDataSource.insertSportItem(SportsEntity(sportUi))
        else
            localDataSource.deleteSportItem(SportsEntity(sportUi))
    }
}