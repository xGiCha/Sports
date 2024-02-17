package com.sport.kaisbet.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sport.kaisbet.domain.models.SportUi

class SportsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun sportToString(sportUi: SportUi): String {
        return gson.toJson(sportUi)
    }

    @TypeConverter
    fun stringToSport(data: String): SportUi {
        val listType = object : TypeToken<SportUi>() {}.type
        return gson.fromJson(data, listType)
    }

}