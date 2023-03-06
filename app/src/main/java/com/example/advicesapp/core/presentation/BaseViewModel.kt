package com.example.advicesapp.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advicesapp.search.domain.ChangeInteractor
import com.example.advicesapp.search.presentation.DispatchersList
import com.example.advicesapp.search.presentation.HandleRequest
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val communication: ChangeFavoriteCommunication,
    private val changeInteractor: ChangeInteractor,
    private val dispatchers: DispatchersList
) : ViewModel(), ChangeFavorite {

    protected fun <T> handle(handleRequest: HandleRequest<T>, block: suspend () -> T) =
        handleRequest.handle(viewModelScope, block)

    override fun changeFavorite(id: Int) {
        communication.map(id)
        viewModelScope.launch(dispatchers.io()) {
            changeInteractor.changeFavorite(id)
        }
    }
}