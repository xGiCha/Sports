package com.sport.kaisbet.data.networkServices

import com.sport.kaisbet.common.Resource
import com.sport.kaisbet.domain.entities.SportsEntity
import retrofit2.http.GET

interface SportsApi {

//    @GET("/api/sports")
    @GET("Http://192.168.2.2:8068/api/sports")
    suspend fun getSports(): SportsEntity

}