package com.sport.kaisbet.presentation.mappers

import com.sport.kaisbet.domain.entities.EventEntity
import com.sport.kaisbet.domain.entities.SportEntity
import com.sport.kaisbet.domain.entities.SportsEntity
import com.sport.kaisbet.domain.models.Event
import com.sport.kaisbet.domain.models.SportUi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class SportsMapperTest {

    private lateinit var sportsMapper: SportsMapper

    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        sportsMapper = SportsMapper()
    }

    @Test
    fun `given empty data when mapSports() is called then it should return empty list`()  {

        /**
         * GIVEN
         * */
        val expected = arrayListOf<SportUi>()

        /**
         * WHEN
         * */
        val result = sportsMapper.mapSports(SportsEntity())

        /**
         * THEN
         * */
        assertTrue(
            result == expected
        )
    }

    @Test
    fun `given valid data when mapSports() is called then it should return valid list`()  {

        /**
         * GIVEN
         * */
        val data = dummySportEntityList
        val expected = dummySportList

        /**
         * WHEN
         * */
        val sportsEntityValue = SportsEntity()
        data.forEach {
            sportsEntityValue.add(it)
        }

        val result = sportsMapper.mapSports(sportsEntityValue)

        /**
         * THEN
         * */
        assertTrue(
            result == expected
        )
    }

    val dummySportEntityList = listOf(
        SportEntity(
            d = "SOCCER",
            i = "FOOT",
            e = listOf(
                EventEntity(
                    d = "Medeama SC - Dreams FC",
                    i = "24456069",
                    si = "FOOT",
                    tt = 1668925680
                )
            )
        ),
        SportEntity(
            d = "BASKETBALL",
            i = "BASK",
            e = listOf(
                EventEntity(
                    d = "Abahani Chittagong - Mohammedan SC",
                    i = "24456069",
                    si = "FOOT",
                    tt = 1668925680
                )
            )
        )
    )

    val dummySportList = listOf(
        SportUi(
            sportName = "SOCCER",
            sportId = "FOOT",
            eventList = listOf(
                Event(
                    eventName = "Medeama SC ",
                    eventSubName = " Dreams FC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680
                )
            )
        ),
        SportUi(
            sportName = "BASKETBALL",
            sportId = "BASK",
            eventList = listOf(
                Event(
                    eventName = "Abahani Chittagong ",
                    eventSubName = " Mohammedan SC",
                    eventId = "24456069",
                    sportId = "FOOT",
                    eventStartTime = 1668925680
                )
            )
        )
    )
}