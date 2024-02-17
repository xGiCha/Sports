package com.sport.kaisbet.common.helper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sport.kaisbet.common.DummyData
import com.sport.kaisbet.common.DummyData.dummyEvent
import com.sport.kaisbet.common.DummyData.dummySportUi
import com.sport.kaisbet.common.DummyData.dummySportUiFavorite
import com.sport.kaisbet.common.DummyData.dummySportUiSwitchState
import com.sport.kaisbet.common.DummyData.dummySportsEntity
import com.sport.kaisbet.data.LocalDataSource
import com.sport.kaisbet.database.SportsEntity
import com.sport.kaisbet.domain.models.SportUi
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.w3c.dom.Entity

@ExperimentalCoroutinesApi
class FavoriteHandlerHelperTest {

    private lateinit var favoriteHandlerHelper: FavoriteHandlerHelper

    @Mock
    private lateinit var mockLocalDataSource: LocalDataSource

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        favoriteHandlerHelper = FavoriteHandlerHelper(mockLocalDataSource)
    }

    @Test
    fun `given a valid sportUiList, event and positive favorite when addSingleStartToFavorite() is called then return a favorite list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)
        val event = dummyEvent
        val hasFavorite = true

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.addSingleStartToFavorite(sportUiList = sportUiList.toMutableList(), event = event, hasFavorite = hasFavorite)

        /**
         * THEN
         * */
        val expected = mutableListOf(dummySportUiFavorite)
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a valid sportUiList, event and negative favorite when addSingleStartToFavorite() is called then return a normal list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)
        val event = dummyEvent
        val hasFavorite = false

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.addSingleStartToFavorite(sportUiList = sportUiList.toMutableList(), event = event, hasFavorite = hasFavorite)

        /**
         * THEN
         * */
        val expected = mutableListOf(dummySportUi)
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a empty sportUiList, event and any favorite when addSingleStartToFavorite() is called then return an empty list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf<SportUi>()
        val event = dummyEvent
        val hasFavorite = true

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.addSingleStartToFavorite(sportUiList = sportUiList.toMutableList(), event = event, hasFavorite = hasFavorite)

        /**
         * THEN
         * */
        val expected = mutableListOf<SportUi>()
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a valid sportUiList, sportUi and positive switchState when addAllStarsToFavorites() is called then return a full favorite list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)
        val sportUi = dummySportUi
        val switchState = true

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.addAllStarsToFavorites(sportUiList = sportUiList.toMutableList(), sportUi = sportUi, switchState = switchState)

        /**
         * THEN
         * */
        val expected = mutableListOf(dummySportUiSwitchState)
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a valid sportUiList, sportUi and negative switchState when addAllStarsToFavorites() is called then return a normal list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)
        val sportUi = dummySportUi
        val switchState = false

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.addAllStarsToFavorites(sportUiList = sportUiList.toMutableList(), sportUi = sportUi, switchState = switchState)

        /**
         * THEN
         * */
        val expected = mutableListOf(dummySportUi)
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a empty sportUiList, sportUi and any switchState when addAllStarsToFavorites() is called then return an empty list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf<SportUi>()
        val sportUi = dummySportUi
        val switchState = false

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.addAllStarsToFavorites(sportUiList = sportUiList.toMutableList(), sportUi = sportUi, switchState = switchState)

        /**
         * THEN
         * */
        val expected = mutableListOf<SportUi>()
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a valid networkList and dbList list when compareNetworkWithDbFavorite() is called then return a favorite list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)
        val dBSportUiList = listOf(dummySportUiFavorite)

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.compareNetworkWithDbFavorite(networkList = sportUiList, dbList = dBSportUiList)

        /**
         * THEN
         * */
        val expected = mutableListOf(dummySportUiFavorite)
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a valid networkList and empty dbList list when compareNetworkWithDbFavorite() is called then return a normal list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)
        val dBSportUiList = listOf<SportUi>()

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.compareNetworkWithDbFavorite(networkList = sportUiList, dbList = dBSportUiList)

        /**
         * THEN
         * */
        val expected = mutableListOf(dummySportUi)
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `given a empty networkList and any dbList list when compareNetworkWithDbFavorite() is called then return an empty list`() = runTest {
        /**
         * GIVEN
         * */
        val sportUiList = listOf<SportUi>()
        val dBSportUiList = listOf<SportUi>()

        /**
         * WHEN
         * */
        val result = favoriteHandlerHelper.compareNetworkWithDbFavorite(networkList = sportUiList, dbList = dBSportUiList)

        /**
         * THEN
         * */
        val expected = mutableListOf<SportUi>()
        assertNotNull(expected)
        assertNotNull(result)
        assertTrue(result == expected)
    }

    @Test
    fun `when fetchFavoriteFromDb() is called then return valid DB list`() = runTest {
        /**
         * GIVEN
         * */
        val collector = mutableListOf<List<SportsEntity>>()
        val sportsEntity = dummySportsEntity
        /**
         * WHEN
         * */
        Mockito.`when`(mockLocalDataSource.getSportsList()).thenReturn(flowOf(listOf(sportsEntity)))

        favoriteHandlerHelper.fetchFavoriteFromDb().collect {
            collector.add(it)
        }

        /**
         * THEN
         * */
        val expected = collector.first().first()
        assertNotNull(expected)
        assert(sportsEntity == expected)
    }

    @Test
    fun `given empty DB when fetchFavoriteFromDb() is called then return empty DB list`() = runTest {
        /**
         * GIVEN
         * */
        val collector = mutableListOf<List<SportsEntity>>()
        val sportsEntity = listOf<SportsEntity>()
        /**
         * WHEN
         * */
        Mockito.`when`(mockLocalDataSource.getSportsList()).thenReturn(flowOf(listOf()))

        favoriteHandlerHelper.fetchFavoriteFromDb().collect {
             collector.add(it)
        }

        /**
         * THEN
         * */
        val expected = collector.firstOrNull()
        assertNotNull(expected)
        assert(sportsEntity == expected)
    }

}

