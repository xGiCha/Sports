package com.sport.kaisbet.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.Sport
import com.sport.kaisbet.domain.repo.SportRemoteRepositoryImpl
import com.sport.kaisbet.presentation.mappers.SportsMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val repository: SportRemoteRepositoryImpl,
    private val sportsMapper: SportsMapper
) : ViewModel() {

    private val _sportsList = MutableLiveData<List<Sport>>()
    val sportsList: LiveData<List<Sport>> = _sportsList

    fun fetchSportsList() {
        viewModelScope.launch {
            kotlin.runCatching {
                repository.getSports()
            }.onFailure {
                it.printStackTrace()
            }.onSuccess {
                _sportsList.postValue(sportsMapper.mapSports(it))
            }
        }
    }

    fun checkCollapsedProperty(isCollapsed: Boolean, position: Int) {
        val sportCollapsedArrayList = _sportsList.value ?: emptyList()
        sportCollapsedArrayList.get(position).isCollapsed = isCollapsed
        _sportsList.postValue(sportCollapsedArrayList)
    }

    fun checkFavoriteProperty(hasFavorite: Boolean, event: Event) {
        var sportEventArrayList: MutableList<Event>
        val sportFavoriteArrayList = _sportsList.value?.toMutableList() ?: mutableListOf()

        sportFavoriteArrayList.forEach { sport ->
            sport.eventList.forEachIndexed { indexEvent, eventItem ->
                if (event != eventItem) return@forEachIndexed
                val newEvent = eventItem.copy()
                newEvent.hasFavorite = hasFavorite
                sportEventArrayList = sport.eventList.map { it.copy() }.toMutableList()

                apply{
                    sportEventArrayList.removeAt(indexEvent)
                    sportEventArrayList.add(0, newEvent)
                }.takeIf { hasFavorite } ?: sportEventArrayList.sortByDescending { it.hasFavorite }

                sport.eventList = sportEventArrayList
            }
        }

        _sportsList.postValue(sportFavoriteArrayList)
    }

}