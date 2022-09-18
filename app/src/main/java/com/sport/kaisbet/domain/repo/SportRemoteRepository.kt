package com.sport.kaisbet.domain.repo

import com.sport.kaisbet.domain.entities.SportsEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface SportRemoteRepository {

    suspend fun getSports(): SportsEntity
}