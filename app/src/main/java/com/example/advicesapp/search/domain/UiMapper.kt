package com.example.advicesapp.search.domain

import com.example.advicesapp.R
import com.example.advicesapp.search.presentation.AdviceUi
import com.example.advicesapp.search.presentation.ProvideResources
import com.example.advicesapp.search.presentation.SearchUiState

class UiMapper(
    private val mapper: Advice.Mapper<AdviceUi>,
    private val resources: ProvideResources
) :
    SearchAdviceResult.Mapper<SearchUiState> {
    override fun map(list: List<Advice>): SearchUiState {
        return SearchUiState.Success(list.map { it.map(mapper) })
    }
    override fun map(exception: DomainException): SearchUiState {
        return SearchUiState.Error(
            when (exception) {
                is DomainException.NoInternetConnection -> resources.string(R.string.no_internet)
                else -> resources.string(R.string.service_error_message)
            }
        )
    }
}