package com.sport.kaisbet.data.repo

import com.sport.kaisbet.common.Resource
import com.sport.kaisbet.domain.entities.SportsEntity
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
interface SportRemoteRepository {

    suspend fun getSports(): SportsEntity
}