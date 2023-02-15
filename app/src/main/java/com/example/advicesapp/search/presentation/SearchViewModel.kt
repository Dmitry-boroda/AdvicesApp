package com.example.advicesapp.search.presentation

import com.example.advicesapp.R
import com.example.advicesapp.search.domain.*

class SearchViewModel(
    private val handleRequest: HandleRequest,
    private val interactor: SearchInteractor,
    changeInteractor: ChangeInteractor,
    private val communication: SearchCommunication,
    communicationFavorite: ChangeFavoriteCommunication,
    dispatchers: DispatchersList,
    private val validation: Validation,
    private val resources: ProvideResources,
) : BaseViewModel(communicationFavorite, changeInteractor, dispatchers) {
    fun advices(query: String) {
        if (validation.isValid(query)) handleRequest.handle {
            interactor.advices(validation.map(query))
        }
        else
            communication.map(SearchUiState.Error(resources.string(R.string.invalid_input_message)))
    }

    fun randomAdvice() = handleRequest.handle {
        interactor.randomAdvice()
    }
}

interface ChangeFavorite {
    fun changeFavorite(id: Int)
}
