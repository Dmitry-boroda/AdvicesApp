package com.example.advicesapp.search.presentation

import com.example.advicesapp.search.data.HandelError
import com.example.advicesapp.search.domain.Advice
import com.example.advicesapp.search.domain.SearchAdviceResult
import com.example.advicesapp.search.domain.UiMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface HandleRequest {

    fun handle(viewModelScope: CoroutineScope, block: suspend () -> SearchAdviceResult)

    class Base(
        private val communication: SearchCommunication,
        private val dispatchers: DispatchersList,
        private val resources: ProvideResources,
        private val mapper: SearchAdviceResult.Mapper<SearchUiState> = UiMapper(
            Advice.Mapper.Base(),
            HandelError.Base(resources)
        )
    ) : HandleRequest {

        override fun handle(
            viewModelScope: CoroutineScope,
            block: suspend () -> SearchAdviceResult
        ) {
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