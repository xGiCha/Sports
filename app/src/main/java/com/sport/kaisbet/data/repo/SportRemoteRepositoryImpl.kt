package com.sport.kaisbet.data.repo

import com.sport.kaisbet.data.networkServices.SportsApi
import com.sport.kaisbet.domain.entities.SportsEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class SportRemoteRepositoryImpl @Inject constructor(
    private val sportsApi: SportsApi
): SportRemoteRepository {

    override suspend fun getSports(): SportsEntity {
        return sportsApi.getSports()
    }
}