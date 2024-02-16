package com.sport.kaisbet.di

import com.sport.kaisbet.data.networkServices.SportsApi
import com.sport.kaisbet.common.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()

    //satisfies provide RetrofitInstance
    @Singleton
    @Provides
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()
    }

    //returns gsonConverterFactory to provideRetrofit
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    //the same as every retrofit builder in order to get our data
    @Singleton
    @Provides
    fun provideRetrofitInstance(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    //this will give the api instance in our RemoteDataSource
    //singleton means we re going to have only one instance of this
    //we're using application scope for this API
    //Provide is if instances must be created with the builder pattern.
    //or if you don't own the class because it comes from external library (Retrofit, Room etc)
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): SportsApi {
        return retrofit.create(SportsApi::class.java)
    }

}