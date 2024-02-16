package com.sport.kaisbet.presentation.mappers

import com.sport.kaisbet.domain.entities.SportsEntity
import com.sport.kaisbet.domain.models.SportUi

interface SportsMapperInterface {

    fun mapSports(sports: SportsEntity) : List<SportUi>
}