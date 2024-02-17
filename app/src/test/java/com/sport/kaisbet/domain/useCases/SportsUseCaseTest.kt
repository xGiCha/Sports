package com.sport.kaisbet.domain.useCases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sport.kaisbet.common.DummyData
import com.sport.kaisbet.common.DummyData.dummySportEntityNetwork
import com.sport.kaisbet.common.DummyData.dummySportUi
import com.sport.kaisbet.common.Resource
import com.sport.kaisbet.common.helper.FavoriteHandlerHelperInterface
import com.sport.kaisbet.data.repo.SportRemoteRepository
import com.sport.kaisbet.domain.entities.SportsEntity
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.presentation.mappers.SportsMapper
import com.sport.kaisbet.presentation.viewModels.SportsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any

@ExperimentalCoroutinesApi
class SportsUseCaseTest {

    lateinit var sportsUseCase: SportsUseCase

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockRepository: SportRemoteRepository
    @Mock
    private lateinit var mockSportsMapper: SportsMapper

    @Before
    fun init() {
        val testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        sportsUseCase = SportsUseCase(mockRepository, mockSportsMapper)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given valid network list when getSports() is called then it should return valid list`() = runTest  {

        /**
         * GIVEN
         * */
        val collector = mutableListOf<Resource<List<SportUi>>>()
        val sportsEntity = SportsEntity()
        sportsEntity.add(dummySportEntityNetwork)

        /**
         * WHEN
         * */
        Mockito.`when`(mockRepository.getSports()).thenReturn(sportsEntity)
        Mockito.`when`(mockSportsMapper.mapSports(any())).thenReturn(listOf(dummySportUi))

        sportsUseCase.fetchSports().collect{
            collector.add(it)
        }

        /**
         * THEN
         * */
        assert(collector[0] is Resource.Loading)
        val successResult = collector[1] as Resource.Success
        val list = successResult.data
        assert(list!!.isNotEmpty())
        assert(list[0] is SportUi)
        val item = list[0]
        assert(!item.isCollapsed)
        assert(!item.switchState)
        assert(item.sportId == "FOOT")
    }
}