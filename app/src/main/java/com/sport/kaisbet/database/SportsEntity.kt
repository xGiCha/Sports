package com.sport.kaisbet.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sport.kaisbet.common.Constants.Companion.SPORTS_TABLE
import com.sport.kaisbet.domain.models.SportUi

@Entity(tableName = SPORTS_TABLE)
data class SportsEntity(
    var sportsUi: SportUi,
    @PrimaryKey(autoGenerate = false)
    val id: String = sportsUi.sportId
)