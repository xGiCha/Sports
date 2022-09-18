package com.sport.kaisbet.data.networkServices

import com.sport.kaisbet.domain.entities.SportsEntity
import retrofit2.http.GET

interface SportsApi {

    @GET("/api/sports")
    suspend fun getSports(): SportsEntity

}