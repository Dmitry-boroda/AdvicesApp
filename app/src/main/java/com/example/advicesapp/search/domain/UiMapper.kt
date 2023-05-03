package com.example.advicesapp.search.domain

import com.example.advicesapp.search.data.HandelError
import com.example.advicesapp.search.presentation.AdviceUi
import com.example.advicesapp.search.presentation.SearchUiState

class UiMapper(
    private val mapper: Advice.Mapper<AdviceUi>,
    private val handelError: HandelError<String>,
) : SearchAdviceResult.Mapper<SearchUiState> {
    override fun map(list: List<Advice>): SearchUiState =
        SearchUiState.Success(list.map { it.map(mapper) })

    override fun map(exception: DomainException): SearchUiState =
        SearchUiState.Error(handelError.handle(exception))
}


