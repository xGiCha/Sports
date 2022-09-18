package com.sport.kaisbet.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.Sport
import com.sport.kaisbet.domain.repo.SportRemoteRepositoryImpl
import com.sport.kaisbet.ui.mappers.SportsMapper
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

    private val _sportsListExtra = MutableLiveData<List<Sport>>()
    val sportsListExtra: LiveData<List<Sport>> = _sportsListExtra

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
        val sportCollapsedArrayList = _sportsList.value
        sportCollapsedArrayList?.get(position)?.isCollapsed = isCollapsed
        _sportsListExtra.postValue(sportCollapsedArrayList)
    }

    fun checkFavoriteProperty(hasFavorite: Boolean, event: Event) {
        var sportEventArrayList: MutableList<Event>
        val sportFavoriteArrayList = getSportFavoriteList()

        sportFavoriteArrayList?.forEach { sport ->
            sport.eventList.forEachIndexed { indexEvent, eventL ->
                if (event != eventL) return@forEachIndexed
                eventL.hasFavorite = hasFavorite
                sportEventArrayList = sport.eventList.toMutableList()

                apply{
                    sportEventArrayList.removeAt(indexEvent)
                    sportEventArrayList.add(0, eventL)
                }.takeIf { hasFavorite } ?: sportEventArrayList.sortByDescending { it.hasFavorite }

                sport.eventList = sportEventArrayList
            }
        }

        _sportsListExtra.postValue(sportFavoriteArrayList)
    }

    private fun getSportFavoriteList() =
        _sportsList.value?.toMutableList().takeIf { _sportsListExtra.value.isNullOrEmpty() }
        ?: _sportsListExtra.value?.toMutableList()

}