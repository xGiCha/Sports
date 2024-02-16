package com.sport.kaisbet.presentation.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sport.kaisbet.domain.entities.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import com.sport.kaisbet.domain.repo.SportRemoteRepositoryImpl
import com.sport.kaisbet.presentation.mappers.SportsMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
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
import retrofit2.HttpException

@ExperimentalCoroutinesApi
class SportsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: SportsViewModel

    @Mock
    private lateinit var mockRepository: SportRemoteRepositoryImpl

    @Mock
    private lateinit var mockSportsMapper: SportsMapper

    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        viewModel = SportsViewModel(
            mockRepository,
            mockSportsMapper
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        runTest {}
    }

    @Test
    fun `given empty data when fetchSportsList() is called then it should return empty list`() = runTest {

        /**
         * WHEN
         * */
        Mockito.`when`(
            mockRepository.getSports()
        ).thenReturn(SportsEntity())
        viewModel.sportsList.observeForever {
            assert(it != null)
        }
        viewModel.fetchSportsList()

        /**
         * THEN
         * */
        assertTrue(
            viewModel.sportsList.value?.isEmpty() == true
        )
    }

    @Test
    fun `given valid data when fetchSportsList() is called then it should return success`() = runTest {

        /**
         * GIVEN
         * */
        val sportList = dummySportList

        /**
         * WHEN
         * */
        Mockito.`when`(mockRepository.getSports()).thenReturn(dummySportsEntityList)
        Mockito.`when`(mockSportsMapper.mapSports(dummySportsEntityList)).thenReturn(dummySportList)
        viewModel.sportsList.observeForever {
            assert(it != null)
        }
        viewModel.fetchSportsList()

        /**
         * THEN
         * */
        assertEquals(
            viewModel.sportsList.value,
            sportList
        )
    }

    @Test
    fun `given fail http request when fetchSportsList() is called then it should return null`() = runTest {

        /**
         * WHEN
         * */
        Mockito.`when`(mockRepository.getSports()).thenThrow(HttpException::class.java)
        viewModel.sportsList.observeForever {
            assert(it != null)
        }
        viewModel.fetchSportsList()

        /**
         * THEN
         * */
        assertNull(
            viewModel.sportsList.value
        )
    }

    @Test
    fun `given valid data when first item in list is collapsed then it should return success`() = runTest {

        /**
         * GIVEN
         * */
        val sportList = dummySportList

        /**
         * WHEN
         * */
        Mockito.`when`(mockRepository.getSports()).thenReturn(dummySportsEntityList)
        Mockito.`when`(mockSportsMapper.mapSports(dummySportsEntityList)).thenReturn(dummySportList)
        viewModel.sportsList.observeForever {
            assert(it != null)
        }
        viewModel.fetchSportsList()

        viewModel.checkCollapsedProperty(true, 0)

        /**
         * THEN
         * */
        assertEquals(
            viewModel.sportsList.value,
            sportList
        )
    }

    @Test
    fun `given valid data when first item in list is not collapsed then it should return success`() = runTest {

        /**
         * GIVEN
         * */
        val sportList = dummySportListSecond

        /**
         * WHEN
         * */
        Mockito.`when`(mockRepository.getSports()).thenReturn(dummySportsEntityList)
        Mockito.`when`(mockSportsMapper.mapSports(dummySportsEntityList)).thenReturn(dummySportListSecond)
        viewModel.sportsList.observeForever {
            assert(it != null)
        }
        viewModel.fetchSportsList()

        viewModel.checkCollapsedProperty(false, 0)

        /**
         * THEN
         * */
        assertEquals(
            viewModel.sportsList.value,
            dummySportListSecond
        )
    }

    @Test
    fun `given valid data when an item in list is favorite then it should return the item in first position`() = runTest {

        /**
         * GIVEN
         * */
        val sportList = dummySportList

        /**
         * WHEN
         * */
        Mockito.`when`(mockRepository.getSports()).thenReturn(dummySportsEntityList)
        Mockito.`when`(mockSportsMapper.mapSports(dummySportsEntityList)).thenReturn(dummySportList)
        viewModel.sportsList.observeForever {
            assert(it != null)
        }
        viewModel.fetchSportsList()

        viewModel.checkFavoriteProperty(true, dummyEvent)

        /**
         * THEN
         * */
        assertEquals(
            viewModel.sportsList.value,
            dummySportListFavorite
        )
    }

    @Test
    fun `given valid data when an item in list is not favorite then return list`() = runTest {

        /**
         * GIVEN
         * */
        val sportList = dummySportList

        /**
         * WHEN
         * */
        Mockito.`when`(mockRepository.getSports()).thenReturn(dummySportsEntityList)
        Mockito.`when`(mockSportsMapper.mapSports(dummySportsEntityList)).thenReturn(sportList)
        viewModel.sportsList.observeForever {
            assert(it != null)
        }
        viewModel.fetchSportsList()

        viewModel.checkFavoriteProperty(false, dummyEvent)

        /**
         * THEN
         * */
        assertEquals(
            viewModel.sportsList.value,
            dummySportListUnFavorite
        )
    }


    val dummySportsEntityList = SportsEntity()

    val dummySportList = listOf(
        SportUi(
            sportName = "SOCCER",
            sportId = "FOOT",
            eventList = listOf(
                Event(
                    eventName = "Medeama SC - Dreams FC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""

                ),
                Event(
                    eventName = "Nigde Belediyespor - Adiyaman 1954 SK",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""
                )
            ),
            isCollapsed = true
        ),
        SportUi(
            sportName = "BASKETBALL",
            sportId = "BASK",
            eventList = listOf(
                Event(
                    eventName = "Abahani Chittagong - Mohammedan SC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""

                )
            )
        )
    )

    val dummySportListSecond = listOf(
        SportUi(
            sportName = "SOCCER",
            sportId = "FOOT",
            eventList = listOf(
                Event(
                    eventName = "Medeama SC - Dreams FC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""

                ),
                Event(
                    eventName = "Nigde Belediyespor - Adiyaman 1954 SK",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""
                )
            ),
            isCollapsed = false
        ),
        SportUi(
            sportName = "BASKETBALL",
            sportId = "BASK",
            eventList = listOf(
                Event(
                    eventName = "Abahani Chittagong - Mohammedan SC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""

                )
            )
        )
    )

    val dummySportListFavorite = listOf(
        SportUi(
            sportName = "SOCCER",
            sportId = "FOOT",
            eventList = listOf(
                Event(
                    eventName = "Nigde Belediyespor - Adiyaman 1954 SK",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    hasFavorite = true,
                    eventSubName = ""
                ),
                Event(
                    eventName = "Medeama SC - Dreams FC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""
                )
            ),
            isCollapsed = true
        ),
        SportUi(
            sportName = "BASKETBALL",
            sportId = "BASK",
            eventList = listOf(
                Event(
                    eventName = "Abahani Chittagong - Mohammedan SC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""
                )
            )
        )
    )

    val dummySportListUnFavorite = listOf(
        SportUi(
            sportName = "SOCCER",
            sportId = "FOOT",
            eventList = listOf(
                Event(
                    eventName = "Nigde Belediyespor - Adiyaman 1954 SK",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    hasFavorite = false,
                    eventSubName = ""
                ),
                Event(
                    eventName = "Medeama SC - Dreams FC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""
                )
            ),
            isCollapsed = true
        ),
        SportUi(
            sportName = "BASKETBALL",
            sportId = "BASK",
            eventList = listOf(
                Event(
                    eventName = "Abahani Chittagong - Mohammedan SC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680,
                    eventSubName = ""
                )
            )
        )
    )

    val dummyEvent = Event(
        eventName = "Nigde Belediyespor - Adiyaman 1954 SK",
        eventId = "24456069",
        sportId = "FOOT",
        eventStartTime = 1668925680,
        eventSubName = ""
    )
}