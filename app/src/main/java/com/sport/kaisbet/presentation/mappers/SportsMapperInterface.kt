package com.sport.kaisbet.presentation.mappers

import com.sport.kaisbet.domain.entities.SportsEntity
import com.sport.kaisbet.domain.models.Sport

interface SportsMapperInterface {

    fun mapSports(sports: SportsEntity) : List<Sport>
}