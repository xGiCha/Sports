package com.sport.kaisbet.presentation.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sport.kaisbet.common.DummyData
import com.sport.kaisbet.common.DummyData.dummyEvent
import com.sport.kaisbet.common.DummyData.dummySportUi
import com.sport.kaisbet.common.DummyData.dummySportUiCollapsed
import com.sport.kaisbet.common.Resource
import com.sport.kaisbet.common.helper.FavoriteHandlerHelperInterface
import com.sport.kaisbet.domain.useCases.SportsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class SportsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SportsViewModel

    @Mock
    private lateinit var mockSportsUseCase: SportsUseCase
    @Mock
    private lateinit var mockFavoriteHandlerUseCase: FavoriteHandlerHelperInterface


    @Before
    fun init() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given valid network list when init is called then it should return success`() = runTest  {

        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)
        val sportsEntity = DummyData.dummySportsEntity

        Mockito.`when`(mockFavoriteHandlerUseCase.fetchFavoriteFromDb()).thenReturn(flowOf(listOf(sportsEntity)))
        Mockito.`when`(mockSportsUseCase.fetchSports()).thenReturn(flow { emit(Resource.Success(listOf(dummySportUi))) })
        Mockito.`when`(mockFavoriteHandlerUseCase.compareNetworkWithDbFavorite(any(), any())).thenReturn(sportUiList)

        /**
         * WHEN
         * */
        viewModel = SportsViewModel(
            mockSportsUseCase,
            mockFavoriteHandlerUseCase
        )
        runCurrent()

        /**
         * THEN
         * */
        assertEquals(sportUiList, viewModel.sportsList.value)
    }

    @Test
    fun `given valid network list and empty db when init is called then it should return success`() = runTest  {

        /**
         * GIVEN
         * */
        val sportUiList = listOf(dummySportUi)

        Mockito.`when`(mockFavoriteHandlerUseCase.fetchFavoriteFromDb()).thenReturn(flowOf(listOf()))
        Mockito.`when`(mockSportsUseCase.fetchSports()).thenReturn(flow { emit(Resource.Error("error",listOf())) })
        Mockito.`when`(mockFavoriteHandlerUseCase.compareNetworkWithDbFavorite(any(), any())).thenReturn(sportUiList)

        /**
         * WHEN
         * */
        viewModel = SportsViewModel(
            mockSportsUseCase,
            mockFavoriteHandlerUseCase
        )
        runCurrent()

        /**
         * THEN
         * */
        assertEquals(true, viewModel.showError.value)
    }

    @Test
    fun `given valid sportUi list  when checkCollapsedProperty() is called then it should return collapsed list`() = runTest  {

        /**
         * GIVEN
         * */
        val sportsEntity = DummyData.dummySportsEntity
        val sportUiList = listOf(dummySportUi)
        val isCollapsed = true

        Mockito.`when`(mockFavoriteHandlerUseCase.fetchFavoriteFromDb()).thenReturn(flowOf(listOf(sportsEntity)))
        Mockito.`when`(mockSportsUseCase.fetchSports()).thenReturn(flow { emit(Resource.Success(listOf(dummySportUi))) })
        Mockito.`when`(mockFavoriteHandlerUseCase.compareNetworkWithDbFavorite(any(), any())).thenReturn(sportUiList)

        /**
         * WHEN
         * */
        viewModel = SportsViewModel(
            mockSportsUseCase,
            mockFavoriteHandlerUseCase
        )
        viewModel.initSportList()
        runCurrent()
        viewModel.checkCollapsedProperty(isCollapsed, 0)
        runCurrent()

        /**
         * THEN
         * */
        val expected = listOf(dummySportUiCollapsed)
        assertEquals(expected, viewModel.sportsList.value)
    }

    @Test
    fun `given valid sportUi list  when checkCollapsedProperty() is called then it should return not collapsed list`() = runTest  {

        /**
         * GIVEN
         * */
        val sportsEntity = DummyData.dummySportsEntity
        val sportUiList = listOf(dummySportUi)
        val isCollapsed = false

        Mockito.`when`(mockFavoriteHandlerUseCase.fetchFavoriteFromDb()).thenReturn(flowOf(listOf(sportsEntity)))
        Mockito.`when`(mockSportsUseCase.fetchSports()).thenReturn(flow { emit(Resource.Success(listOf(dummySportUi))) })
        Mockito.`when`(mockFavoriteHandlerUseCase.compareNetworkWithDbFavorite(any(), any())).thenReturn(sportUiList)

        /**
         * WHEN
         * */
        viewModel = SportsViewModel(
            mockSportsUseCase,
            mockFavoriteHandlerUseCase
        )
        viewModel.initSportList()
        runCurrent()
        viewModel.checkCollapsedProperty(isCollapsed, 0)
        runCurrent()

        /**
         * THEN
         * */
        val expected = listOf(dummySportUi)
        assertEquals(expected, viewModel.sportsList.value)
    }

    @Test
    fun `given valid sportUi list  when handleFavoriteSingleStar() is called then it should return list with a single item as favorite`() = runTest  {

        /**
         * GIVEN
         * */
        val sportsEntity = DummyData.dummySportsEntity
        val sportUiList = mutableListOf(dummySportUi)
        val hasFavorite = false

        Mockito.`when`(mockFavoriteHandlerUseCase.fetchFavoriteFromDb()).thenReturn(flowOf(listOf(sportsEntity)))
        Mockito.`when`(mockSportsUseCase.fetchSports()).thenReturn(flow { emit(Resource.Success(listOf(dummySportUi))) })
        Mockito.`when`(mockFavoriteHandlerUseCase.compareNetworkWithDbFavorite(any(), any())).thenReturn(sportUiList)
        Mockito.`when`(mockFavoriteHandlerUseCase.addSingleStartToFavorite(any(), any(), any())).thenReturn(sportUiList)

        /**
         * WHEN
         * */
        viewModel = SportsViewModel(
            mockSportsUseCase,
            mockFavoriteHandlerUseCase
        )
        viewModel.initSportList()
        runCurrent()
        viewModel.handleFavoriteSingleStar(hasFavorite, dummyEvent)
        runCurrent()

        /**
         * THEN
         * */
        val expected = listOf(dummySportUi)
        assertEquals(expected, viewModel.sportsList.value)
    }

    @Test
    fun `given valid sportUi list  when handleFavoriteAllStars() is called then it should return whole favorite list`() = runTest  {

        /**
         * GIVEN
         * */
        val sportsEntity = DummyData.dummySportsEntity
        val sportUiList = mutableListOf(dummySportUi)
        val hasFavorite = false

        Mockito.`when`(mockFavoriteHandlerUseCase.fetchFavoriteFromDb()).thenReturn(flowOf(listOf(sportsEntity)))
        Mockito.`when`(mockSportsUseCase.fetchSports()).thenReturn(flow { emit(Resource.Success(listOf(dummySportUi))) })
        Mockito.`when`(mockFavoriteHandlerUseCase.compareNetworkWithDbFavorite(any(), any())).thenReturn(sportUiList)
        Mockito.`when`(mockFavoriteHandlerUseCase.addAllStarsToFavorites(any(), any(), any())).thenReturn(sportUiList)

        /**
         * WHEN
         * */
        viewModel = SportsViewModel(
            mockSportsUseCase,
            mockFavoriteHandlerUseCase
        )
        viewModel.initSportList()
        runCurrent()
        viewModel.handleFavoriteSingleStar(hasFavorite, dummyEvent)
        runCurrent()

        /**
         * THEN
         * */
        val expected = listOf(dummySportUi)
        assertEquals(expected, viewModel.sportsList.value)
    }

}