package com.example.advicesapp.search.presentation

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.example.advicesapp.R
import com.example.advicesapp.core.presentation.ChangeFavoriteCommunication
import com.example.advicesapp.search.domain.*

class SearchViewModelTest {

    private lateinit var interactor: FakeInteractor
    private lateinit var communication: FakeSearchCommunication
    private lateinit var communicationFavorite: FakeCommunicationFavorites
    private lateinit var dispatchers: FakeDispatchers
    private lateinit var validation: FakeValidation
    private lateinit var resources: FakeResources
    private lateinit var viewModel: SearchViewModel
    private lateinit var handleRequest: HandleRequest<SearchAdviceResult>

    @Before
    fun setUp() {
        interactor = FakeInteractor()
        communication = FakeSearchCommunication()
        communicationFavorite = FakeCommunicationFavorites()
        dispatchers = FakeDispatchers()
        validation = FakeValidation()
        resources = FakeResources()
        handleRequest = HandleRequest.HandleSearchRequest(
            communication = communication,
            dispatchers = dispatchers,
            resources = resources,
        )
        viewModel =
            SearchViewModel(
                handleRequest = handleRequest,
                interactor = interactor,
                communication = communication,
                communicationFavorite = communicationFavorite,
                dispatchers = dispatchers,
                validation = Valid.Base(resources, communication, validation)
            )
    }

    @Test
    fun `search invalid query`() = runBlocking {
        validation.isValid = false

        viewModel.advices(query = "a")

        assertEquals(0, interactor.queryList.size)
        assertEquals(0, interactor.randomAdviceCallCount)
        assertEquals(0, dispatchers.ioCallCount)
        assertEquals("a", validation.isValidCallList[0])
        assertEquals(1, validation.isValidCallList.size)
        assertEquals(0, validation.mapCallList.size)
        assertEquals(SearchUiState.Error(message = "invalid"), communication.statesList[0])
        assertEquals(1, communication.statesList.size)
    }

    @Test
    fun `search valid query result error`() = runBlocking {
        validation.isValid = true
        validation.mapResult = "b"
        interactor.searchAdviceResultByQuery =
            SearchAdviceResult.Error(exception = DomainException.ServiceUnavailable())

        viewModel.advices(query = "a")

        assertEquals(1, validation.isValidCallList.size)
        assertEquals("a", validation.isValidCallList[0])
        assertEquals(1, validation.mapCallList.size)
        assertEquals(SearchUiState.Progress, communication.statesList[0])
        assertEquals(1, dispatchers.ioCallCount)
        assertEquals("b", interactor.queryList[0])
        assertEquals(1, interactor.queryList.size)
        assertEquals(0, interactor.randomAdviceCallCount)
        assertEquals(SearchUiState.Error(message = "service"), communication.statesList[1])
        assertEquals(2, communication.statesList.size)
    }

    @Test
    fun `search valid query result success`() = runBlocking {
        validation.isValid = true
        validation.mapResult = "b"
        interactor.searchAdviceResultByQuery =
            SearchAdviceResult.Success(
                list = listOf<Advice>(
                    Advice(
                        id = 1,
                        query = "x",
                        text = "x",
                        isFavorite = false
                    )
                )
            )

        viewModel.advices(query = "a")

        assertEquals(1, validation.isValidCallList.size)
        assertEquals("a", validation.isValidCallList[0])
        assertEquals(1, validation.mapCallList.size)
        assertEquals(SearchUiState.Progress, communication.statesList[0])
        assertEquals(1, dispatchers.ioCallCount)
        assertEquals("b", interactor.queryList[0])
        assertEquals(1, interactor.queryList.size)
        assertEquals(0, interactor.randomAdviceCallCount)
        assertEquals(
            SearchUiState.Success(
                list = listOf<AdviceUi>(
                    AdviceUi(
                        id = 1,
                        query = "x",
                        text = "x",
                        isFavorite = false
                    )
                )
            ), communication.statesList[1]
        )
        assertEquals(2, communication.statesList.size)
    }

    @Test
    fun `random advice result error`() = runBlocking {

        interactor.searchAdviceRandomResult =
            SearchAdviceResult.Error(exception = DomainException.ServiceUnavailable())

        viewModel.randomAdvice()

        assertEquals(SearchUiState.Progress, communication.statesList[0])
        assertEquals(1, dispatchers.ioCallCount)
        assertEquals(1, interactor.randomAdviceCallCount)
        assertEquals(0, interactor.queryList.size)
        assertEquals(SearchUiState.Error(message = "service"), communication.statesList[1])
        assertEquals(2, communication.statesList.size)
    }

    @Test
    fun `random advice result success`() = runBlocking {
        interactor.searchAdviceRandomResult =
            SearchAdviceResult.Success(
                list = listOf<Advice>(
                    Advice(
                        id = 1,
                        query = "x",
                        text = "x",
                        isFavorite = true
                    )
                )
            )

        viewModel.randomAdvice()

        assertEquals(SearchUiState.Progress, communication.statesList[0])
        assertEquals(1, dispatchers.ioCallCount)
        assertEquals(0, interactor.queryList.size)
        assertEquals(1, interactor.randomAdviceCallCount)
        assertEquals(
            SearchUiState.Success(
                list = listOf<AdviceUi>(
                    AdviceUi(
                        id = 1,
                        query = "x",
                        text = "x",
                        isFavorite = true
                    )
                )
            ), communication.statesList[1]
        )
        assertEquals(2, communication.statesList.size)
    }

    @Test
    fun `change status favorites`() = runBlocking {

        viewModel.changeFavorite(
            item = AdviceUi(
                id = 1,
                query = "x",
                text = "x",
                isFavorite = false
            )
        )

        assertEquals(1, communicationFavorite.stateChangeList.size)
        assertEquals(1, dispatchers.ioCallCount)
        assertEquals(1, interactor.idList.size)
    }

}

private class FakeInteractor : SearchInteractor {
    val queryList = ArrayList<String>()
    var searchAdviceResultByQuery: SearchAdviceResult? = null

    override suspend fun advices(query: String): SearchAdviceResult {
        queryList.add(query)
        return searchAdviceResultByQuery!!
    }

    var randomAdviceCallCount = 0
    var searchAdviceRandomResult: SearchAdviceResult? = null
    override suspend fun randomAdvice(): SearchAdviceResult {
        randomAdviceCallCount++
        return searchAdviceRandomResult!!
    }

    val idList = ArrayList<AdviceUi>()
    override suspend fun changeFavorite(item: AdviceUi) {
        idList.add(item)
    }
}

private class FakeSearchCommunication : SearchCommunication {
    val statesList = ArrayList<SearchUiState>()
    override fun map(data: SearchUiState) {
        statesList.add(data)
    }
}

private class FakeCommunicationFavorites() : ChangeFavoriteCommunication {
    val stateChangeList = ArrayList<AdviceUi>()
    override fun map(item: AdviceUi) {
        stateChangeList.add(item)
    }
}

private class FakeDispatchers : DispatchersList {
    private val testDispatcher = TestCoroutineDispatcher()
    var ioCallCount = 0
    override fun io(): CoroutineDispatcher {
        ioCallCount++
        return testDispatcher
    }

    override fun ui(): CoroutineDispatcher {
        return testDispatcher
    }
}

private class FakeValidation : Validation {

    val isValidCallList = ArrayList<String>()
    var isValid: Boolean? = null
    override fun isValid(query: String): Boolean {
        isValidCallList.add(query)
        return isValid!!
    }

    val mapCallList = ArrayList<String>()
    var mapResult: String? = null
    override fun map(data: String): String {
        mapCallList.add(data)
        return mapResult!!
    }

}

private class FakeResources : ProvideResources {

    override fun string(id: Int): String {
        return when (id) {
            R.string.invalid_input_message -> "invalid"
            R.string.service_error_message -> "service"
            else -> throw java.lang.IllegalStateException()
        }
    }

}