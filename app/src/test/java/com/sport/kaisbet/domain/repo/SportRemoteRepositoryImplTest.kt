package com.sport.kaisbet.domain.repo

import com.sport.kaisbet.data.networkServices.SportsApi
import com.sport.kaisbet.domain.entities.SportsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.anyVararg
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class SportRemoteRepositoryImplTest {

    private lateinit var sportRemoteRepositoryImpl: SportRemoteRepositoryImpl

    @Mock
    private lateinit var mockSportsApi: SportsApi

    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        sportRemoteRepositoryImpl = SportRemoteRepositoryImpl(mockSportsApi)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        runTest {}
    }

    @Test
    fun `verify getSports() should call api services `() = runTest {

        sportRemoteRepositoryImpl.getSports()

        Mockito.verify(mockSportsApi).getSports()

    }
}