package com.sport.kaisbet.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [SportsEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(SportsTypeConverter::class)
abstract class SportsDatabase: RoomDatabase() {

    abstract fun sportsDao(): SportsDao

}