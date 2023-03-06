package com.example.advicesapp.search.presentation

import com.example.advicesapp.core.presentation.BaseViewModel
import com.example.advicesapp.core.presentation.ChangeFavoriteCommunication
import com.example.advicesapp.search.domain.*

class SearchViewModel(
    private val handleRequest: HandleRequest<SearchAdviceResult>,
    private val interactor: SearchInteractor,
    private val communication: SearchCommunication,
    communicationFavorite: ChangeFavoriteCommunication,// TODO: inherit
    dispatchers: DispatchersList,
    private val validation: Valid,
) : BaseViewModel(communicationFavorite, interactor, dispatchers) {

    fun advices(query: String) = validation.isValid(query) { filteredQuery ->
        handle(handleRequest) { interactor.advices(filteredQuery) }
    }

    fun randomAdvice() = handle(handleRequest) { interactor.randomAdvice() }
}