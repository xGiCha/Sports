package com.sport.kaisbet.data

import com.sport.kaisbet.common.DummyData
import com.sport.kaisbet.common.DummyData.dummySportUi
import com.sport.kaisbet.database.SportsDao
import com.sport.kaisbet.database.SportsEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LocalDataSourceTest {

    @Mock
    private lateinit var mockSportsDao: SportsDao

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        localDataSource = LocalDataSource(mockSportsDao)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }
    @Test
    fun `getSportsList should return flow of sports entities`() = runBlocking  {
        /**
         * GIVEN
         * */
        val sportsEntities = listOf(
            SportsEntity(dummySportUi, "FOOT")
        )
        val flowOfSportsEntities = flowOf(sportsEntities)
        Mockito.`when`(mockSportsDao.getSportsList()).thenReturn(flowOfSportsEntities)

        /**
         * WHEN
         * */
        val resultFlow = localDataSource.getSportsList()

        /**
         * THEN
         * */
        assertEquals(sportsEntities, resultFlow.first())
    }

    @Test
    fun `insertSportItem should insert sport entity`() = runBlocking {
        /**
         * GIVEN
         * */
        val sportsEntity = SportsEntity(dummySportUi, "FOOT")

        /**
         * WHEN
         * */
        localDataSource.insertSportItem(sportsEntity)

        /**
         * THEN
         * */
        verify(mockSportsDao).insertSportItem(sportsEntity)
    }

    @Test
    fun `deleteSportItem should delete sport entity`() = runBlocking {
        /**
         * GIVEN
         * */
        val sportsEntity = SportsEntity(dummySportUi, "FOOT")

        /**
         * WHEN
         * */
        localDataSource.deleteSportItem(sportsEntity)

        /**
         * THEN
         * */
        verify(mockSportsDao).deleteSportItem(sportsEntity)
    }
}