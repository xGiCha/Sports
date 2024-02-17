package com.sport.kaisbet.data

import com.sport.kaisbet.database.SportsDao
import com.sport.kaisbet.database.SportsEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sportsDao: SportsDao
) {

    fun getSportsList(): Flow<List<SportsEntity>> {
        return sportsDao.getSportsList()
    }

    fun insertSportItem(sportsEntity: SportsEntity) {
        sportsDao.insertSportItem(sportsEntity)
    }

    fun deleteSportItem(sportsEntity: SportsEntity) {
        sportsDao.deleteSportItem(sportsEntity)
    }

}