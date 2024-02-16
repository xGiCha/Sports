package com.sport.kaisbet.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sport.kaisbet.common.Resource
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.domain.useCases.FavoriteHandlerUseCase
import com.sport.kaisbet.domain.useCases.SportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SportsViewModel @Inject constructor(
    private val sportsUseCase: SportsUseCase,
    private val favoriteHandlerUseCase: FavoriteHandlerUseCase
) : ViewModel() {

    private val _sportsList = MutableSharedFlow<List<SportUi>>()
    val sportsList: SharedFlow<List<SportUi>> = _sportsList
    private val _loader = MutableStateFlow(false)
    val loader: StateFlow<Boolean> = _loader
    lateinit var _localList: List<SportUi>

    init {
        fetchSportsList()
    }

    fun fetchSportsList() {
        viewModelScope.launch {
            sportsUseCase.fetchSports().collectLatest {
                when (it) {
                    is Resource.Success -> {
                        displayData(it.data ?: emptyList())
                        displayLoader(false)
                    }
                    is Resource.Loading -> {
                        displayLoader(true)
                    }
                    else -> {
                        displayData(emptyList())
                        displayLoader(false)
                    }
                }
            }
        }
    }

    fun checkCollapsedProperty(isCollapsed: Boolean, position: Int) {
        val sportCollapsedArrayList = _localList
        sportCollapsedArrayList.get(position).isCollapsed = isCollapsed
        displayData(sportCollapsedArrayList)
    }

    fun handleFavoriteSingleStar(hasFavorite: Boolean, event: Event) {
        val list = favoriteHandlerUseCase.addSingleStartToFavorite(hasFavorite, event, _localList.toMutableList())
        displayData(list)
    }

    fun handleFavoriteAllStars(switchState: Boolean, sportUi: SportUi) {
        val list = favoriteHandlerUseCase.addAllStarsToFavorites(switchState, sportUi, _localList.toMutableList())
        displayData(list)
    }

    private fun displayData(list: List<SportUi>) {
        _localList = list
        viewModelScope.launch {
            _sportsList.emit(list)
        }
    }

    private fun displayLoader(state: Boolean) {
        viewModelScope.launch {
            _loader.emit(state)
        }
    }

}