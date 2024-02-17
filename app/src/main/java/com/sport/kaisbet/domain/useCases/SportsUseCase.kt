package com.sport.kaisbet.domain.useCases

import com.sport.kaisbet.common.Resource
import com.sport.kaisbet.data.repo.SportRemoteRepository
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.data.repo.SportRemoteRepositoryImpl
import com.sport.kaisbet.presentation.mappers.SportsMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class SportsUseCase @Inject constructor(
    private val repository: SportRemoteRepository,
    private val sportsMapper: SportsMapper
) {

    fun fetchSports(): Flow<Resource<List<SportUi>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getSports()
            emit(Resource.Success(sportsMapper.mapSports(response)))
        } catch (e : Exception){
            emit(Resource.Error("Something went wrong"))
        }
    }
}