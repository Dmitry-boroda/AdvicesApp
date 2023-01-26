package com.example.advicesapp.search.presentation

interface SearchUiState {
    data class Success(private val list: List<AdviceUi>) : SearchUiState

    data class Error(private val message: String) : SearchUiState

    object Progress: SearchUiState
}