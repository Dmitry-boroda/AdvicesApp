package com.example.advicesapp.search.presentation

import com.example.advicesapp.core.presentation.Communication

interface SearchCommunication : Communication.Mutable<SearchUiState> {

    class Base : Communication.Abstract<SearchUiState>(), SearchCommunication
}