package com.example.advicesapp.search.presentation

import kotlinx.coroutines.CoroutineDispatcher

interface DispatchersList {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
}