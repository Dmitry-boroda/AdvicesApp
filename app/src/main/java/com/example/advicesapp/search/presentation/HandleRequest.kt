package com.example.advicesapp.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advicesapp.search.domain.Advice
import com.example.advicesapp.search.domain.ChangeInteractor
import com.example.advicesapp.search.domain.SearchAdviceResult
import com.example.advicesapp.search.domain.UiMapper
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface HandleRequest {
    fun handle(block: suspend () -> SearchAdviceResult)
    abstract class BaseViewModel(
        private val communication: ChangeFavoriteCommunication,
        private val changeInteractor: ChangeInteractor,
        private val dispatchers: DispatchersList
    ) : ViewModel(), ChangeFavorite {
        override fun changeFavorite(id: Int) {
            communication.map(id)
            viewModelScope.launch(dispatchers.io()) {
                changeInteractor.changeFavorite(id)
            }
        }
    }

    interface ChangeFavorite {
        fun changeFavorite(id: Int)
    }

    abstract class Base(
        private val communication: SearchCommunication,
        communicationFavorite: ChangeFavoriteCommunication,
        changeInteractor: ChangeInteractor,
        private val dispatchers: DispatchersList,
        private val resources: ProvideResources,
        private val mapper: SearchAdviceResult.Mapper<SearchUiState> = UiMapper(
            Advice.Mapper.Base(),
            resources
        )
    ) : BaseViewModel(communicationFavorite,changeInteractor,dispatchers), HandleRequest {
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