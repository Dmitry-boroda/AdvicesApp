package com.example.advicesapp.search.presentation

import com.example.advicesapp.R
import com.example.advicesapp.search.domain.*

class SearchViewModel(
    private val interactor: SearchInteractor,
    changeInteractor: ChangeInteractor,
    private val communication: SearchCommunication,
    communicationFavorite: ChangeFavoriteCommunication,
    dispatchers: DispatchersList,
    private val validation: Validation,
    private val resources: ProvideResources,
    mapper: SearchAdviceResult.Mapper<SearchUiState> = UiMapper(
        Advice.Mapper.Base(),
        resources
    )
) : HandleRequest.Base(
    communication,
    communicationFavorite,
    changeInteractor,
    dispatchers,
    resources,
    mapper
) {
    fun advices(query: String) {
        if (validation.isValid(query)) handle {
            interactor.advices(validation.map(query))
        }
        else
            communication.map(SearchUiState.Error(resources.string(R.string.invalid_input_message)))
    }

    fun randomAdvice() = handle {
        interactor.randomAdvice()
    }

}

