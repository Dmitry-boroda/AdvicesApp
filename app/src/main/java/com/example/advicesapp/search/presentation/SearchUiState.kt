package com.example.advicesapp.search.presentation

interface SearchUiState {

    class Success(private val list: List<AdviceUi>) : SearchUiState {

    }

    class Error(private val message: String) : SearchUiState {

    }

    class Progress() : SearchUiState {

    }
}