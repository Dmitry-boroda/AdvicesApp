package com.example.advicesapp.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.advicesapp.search.domain.ChangeInteractor
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val communication: ChangeFavoriteCommunication,
    private val changeInteractor: ChangeInteractor,
    private val dispatchers: DispatchersList
) : ViewModel(), ChangeFavorite {
    override fun changeFavorite(id: Int) {
        communication.map(id)
        viewModelScope.launch(dispatchers.io()) {
            changeInteractor.changeFavorite(id)
        }
    }
}