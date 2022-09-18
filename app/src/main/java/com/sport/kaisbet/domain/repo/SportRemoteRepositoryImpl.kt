package com.sport.kaisbet.domain.repo

import com.sport.kaisbet.data.networkServices.SportsApi
import com.sport.kaisbet.domain.entities.SportsEntity
import javax.inject.Inject

class SportRemoteRepositoryImpl @Inject constructor(
    private val sportsApi: SportsApi
): SportRemoteRepository {

    override suspend fun getSports(): SportsEntity {
        return sportsApi.getSports()
    }
}