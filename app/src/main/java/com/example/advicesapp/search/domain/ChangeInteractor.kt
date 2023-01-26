package com.example.advicesapp.search.domain

interface ChangeInteractor {
    suspend fun changeFavorite(id: Int): Unit
}