package com.sport.kaisbet.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SportsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSportItem(sportsEntity: SportsEntity)

    @Query("SELECT * FROM sports_table")
    fun getSportsList(): Flow<List<SportsEntity>>

    @Delete
    fun deleteSportItem(sportsEntity: SportsEntity)

}