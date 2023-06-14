package com.example.advicesapp.search.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SearchInteractorTest {

    private lateinit var interactor: SearchInteractor
    private lateinit var repository: TestSearchRepository
    private lateinit var managerResource: TestManagerResources

    @Before
    fun setUp() {
        repository = TestSearchRepository()
        interactor = SearchInteractor.Base(
            repository,
            managerResource,
        )
    }

    @Test
    fun `test advice success`() = runBlocking {
        repository.changeExpectOfAdvice(
            Advice(1, "query", "advice", true)
        )

        val actual = interactor.advices("query")

        val expected = SearchAdviceResult.Success(
            listOf(
                Advice(1, "query", "advice", true)
            )
        )

        assertEquals(expected, actual)
        assertEquals("query", repository.adviceQueryCallList[0])
        assertEquals(1, repository.adviceQueryCallList.size)
    }

    @Test
    fun `test advice error`() = runBlocking {
        repository.expectingErrorGetAdvice(true)
        managerResource.changeExpected("No Internet Connection")

        val actual = interactor.advices("query")

        val expected = SearchAdviceResult.Error(DomainException.NoInternetConnection())

        assertEquals(expected, actual)
        assertEquals("query", repository.adviceQueryCallList[0])
        assertEquals(1, repository.adviceQueryCallList.size)
    }

    @Test
    fun `test random advice success`() = runBlocking {
        repository.changeExpectOfAdvice(
            Advice(1, "query", "advice", true)
        )

        val actual = interactor.randomAdvice()

        val expected = SearchAdviceResult.Success(
            listOf(
                Advice(1, "query", "advice", true)
            )
        )

        assertEquals(expected, actual)
        assertEquals(1, repository.randomAdviceCallList.size)
    }

    @Test
    fun `test random advice error`() = runBlocking {
        repository.expectingErrorGetRandomAdvice(true)
        managerResource.changeExpected("No Internet Connection")

        val actual = interactor.randomAdvice()

        val expected = SearchAdviceResult.Error(DomainException.NoInternetConnection())

        assertEquals(expected, actual)
        assertEquals(1, repository.randomAdviceCallList.size)
    }

    private class TestSearchRepository : SearchRepository {

        private var advice = Advice(1, "query", "advice", true)
        private var errorWhileAdvice = false


        var adviceQueryCallList = mutableListOf<String>()
        var randomAdviceCallList = mutableListOf<String>()

        fun changeExpectOfAdvice(advice: Advice) {
            this.advice = advice
        }

        fun expectingErrorGetAdvice(error: Boolean) {
            errorWhileAdvice = error
        }

        fun expectingErrorGetRandomAdvice(error: Boolean) {
            errorWhileAdvice = error
        }

        override suspend fun adviceQuery(query: String): Advice {
            adviceQueryCallList.add(query)

            if (errorWhileAdvice)
                throw DomainException.NoInternetConnection()
            return advice
        }

        override suspend fun randomAdvice(): Advice {
            randomAdviceCallList.add("")

            if (errorWhileAdvice)
                throw DomainException.NoInternetConnection()
            return advice
        }
    }

    private class TestManagerResources : ManagerResources {

        private var value = ""

        fun changeExpected(string: String) {
            value = string
        }

        override fun string(id: Int): String = value
    }
}