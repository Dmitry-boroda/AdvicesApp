package com.example.advicesapp.search.presentation

import androidx.lifecycle.ViewModel
import com.example.advicesapp.search.domain.SearchInteractor

class SearchViewModel(
    private val interactor: SearchInteractor,
    private val communication: SearchCommunication,
    private val dispatchers: DispatchersList,
    private val validation: Validation,
    private val resources: ProvideResources,
) : ViewModel() {
    suspend fun advices(query: String) {
        if (query.isEmpty()) {
            //
        } else {
            interactor.advices(query)
        }
    }
    suspend fun randomAdvice() {
        interactor.randomAdvice()
    }
}
