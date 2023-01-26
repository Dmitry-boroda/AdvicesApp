package com.example.advicesapp.search.domain

import com.example.advicesapp.R
import com.example.advicesapp.search.presentation.AdviceUi
import com.example.advicesapp.search.presentation.ProvideResources
import com.example.advicesapp.search.presentation.SearchUiState

sealed class SearchAdviceResult {
    interface Mapper<T> {
        fun map(list: List<Advice>): T
        fun map(exception: DomainException): T
    }

    abstract fun <T> map(mapper: Mapper<T>): T

    data class Success(private val list: List<Advice> = emptyList()) : SearchAdviceResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list)
    }

    data class Error(private val exception: DomainException) : SearchAdviceResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(exception)
    }
}

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