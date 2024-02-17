package com.sport.kaisbet.common.helper

import com.sport.kaisbet.database.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow

@ActivityRetainedScoped
interface FavoriteHandlerHelperInterface {

    fun addSingleStartToFavorite(
        hasFavorite: Boolean,
        event: Event,
        sportUiList: MutableList<SportUi>
    ): MutableList<SportUi>

    fun addAllStarsToFavorites(
        switchState: Boolean,
        sportUi: SportUi,
        sportUiList: MutableList<SportUi>
    ): MutableList<SportUi>

    fun fetchFavoriteFromDb(): Flow<List<SportsEntity>>

    fun compareNetworkWithDbFavorite(
        networkList: List<SportUi>,
        dbList: List<SportUi>
    ): List<SportUi>
}