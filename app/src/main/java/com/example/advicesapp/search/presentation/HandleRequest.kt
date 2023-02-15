package com.example.advicesapp.search.presentation

import androidx.lifecycle.viewModelScope
import com.example.advicesapp.search.domain.Advice
import com.example.advicesapp.search.domain.ChangeInteractor
import com.example.advicesapp.search.domain.SearchAdviceResult
import com.example.advicesapp.search.domain.UiMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface HandleRequest {
    fun handle(block: suspend () -> SearchAdviceResult)
    class Base(
        private val communication: SearchCommunication,
        communicationFavorite: ChangeFavoriteCommunication,
        changeInteractor: ChangeInteractor,
        private val dispatchers: DispatchersList,
        private val resources: ProvideResources,
        private val mapper: SearchAdviceResult.Mapper<SearchUiState> = UiMapper(
            Advice.Mapper.Base(),
            resources
        )
    ) : BaseViewModel(communicationFavorite, changeInteractor, dispatchers), HandleRequest {
        override fun handle(block: suspend () -> SearchAdviceResult) {
            communication.map(SearchUiState.Progress)
            viewModelScope.launch(dispatchers.io()) {
                val result = block.invoke()
                val uiState = result.map(mapper)
                withContext(dispatchers.ui()) {
                    communication.map(uiState)
                }
            }
        }
    }
}