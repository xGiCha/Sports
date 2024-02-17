package com.sport.kaisbet.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sport.kaisbet.common.Resource
import com.sport.kaisbet.database.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.common.helper.FavoriteHandlerHelper
import com.sport.kaisbet.common.helper.FavoriteHandlerHelperInterface
import com.sport.kaisbet.domain.useCases.SportsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val favoriteHandlerUseCase: FavoriteHandlerHelperInterface
) : ViewModel() {

    private val _sportsList = MutableStateFlow<List<SportUi>>(emptyList())
    val sportsList: StateFlow<List<SportUi>> = _sportsList
    private val _loader = MutableStateFlow(false)
    val loader: StateFlow<Boolean> = _loader
    private val _showError = MutableStateFlow(false)
    val showError: StateFlow<Boolean> = _showError
    lateinit var _localList: List<SportUi>

    init {
        initSportList()
    }

    fun initSportList() {
        viewModelScope.launch {
            favoriteHandlerUseCase.fetchFavoriteFromDb().collectLatest { sportsEntityList ->
                fetchSportsList(sportsEntityList)
            }
        }
    }

    private fun fetchSportsList(dbFavoriteList: List<SportsEntity>) {
        viewModelScope.launch {
            sportsUseCase.fetchSports().collectLatest {
                when (it) {
                    is Resource.Success -> {
                        val data = favoriteHandlerUseCase.compareNetworkWithDbFavorite(it.data ?: emptyList(),
                            dbFavoriteList.map { it.sportsUi })
                        displayData(data)
                        displayLoader(false)
                    }
                    is Resource.Loading -> {
                        displayLoader(true)
                    }
                    else -> {
                        displayError(true)
                        displayLoader(false)
                    }
                }
            }
        }
    }

    fun checkCollapsedProperty(isCollapsed: Boolean, position: Int) {
        val updatedList = _localList.toMutableList().apply {
            set(position, get(position).copy(isCollapsed = isCollapsed))
        }
        displayData(updatedList)
    }

    fun handleFavoriteSingleStar(hasFavorite: Boolean, event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = favoriteHandlerUseCase.addSingleStartToFavorite(
                hasFavorite,
                event,
                _localList.toMutableList()
            )
            displayData(list)
        }
    }

    fun handleFavoriteAllStars(switchState: Boolean, sportUi: SportUi) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = favoriteHandlerUseCase.addAllStarsToFavorites(
                switchState,
                sportUi,
                _localList.toMutableList()
            )
            displayData(list)
        }
    }

    private fun displayData(list: List<SportUi>) {
        _localList = list
        viewModelScope.launch {
            _sportsList.emit(list)
        }
    }

    private fun displayError(hasError: Boolean) {
        viewModelScope.launch {
            _showError.emit(hasError)
        }
    }

    private fun displayLoader(state: Boolean) {
        viewModelScope.launch {
            _loader.emit(state)
        }
    }

}