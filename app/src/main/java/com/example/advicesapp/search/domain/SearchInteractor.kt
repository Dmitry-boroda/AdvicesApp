package com.example.advicesapp.search.domain

interface SearchInteractor : ChangeInteractor {
    suspend fun advices(query: String): SearchAdviceResult
    suspend fun randomAdvice(): SearchAdviceResult
}