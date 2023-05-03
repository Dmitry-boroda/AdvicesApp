package com.example.advicesapp.search.domain

import com.example.advicesapp.search.presentation.AdviceUi

interface ChangeInteractor {
    suspend fun changeFavorite(item: AdviceUi)
}