package com.sport.kaisbet.di

import com.sport.kaisbet.common.helper.FavoriteHandlerHelper
import com.sport.kaisbet.common.helper.FavoriteHandlerHelperInterface
import com.sport.kaisbet.data.LocalDataSource
import com.sport.kaisbet.data.networkServices.SportsApi
import com.sport.kaisbet.data.repo.SportRemoteRepository
import com.sport.kaisbet.data.repo.SportRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideFavoriteHandlerHelperInterface(
        localDataSource: LocalDataSource,
    ): FavoriteHandlerHelperInterface {
        return FavoriteHandlerHelper(localDataSource)
    }

    @Singleton
    @Provides
    fun provideSportRemoteRepository(
        sportsApi: SportsApi,
    ): SportRemoteRepository {
        return SportRemoteRepositoryImpl(sportsApi)
    }

}