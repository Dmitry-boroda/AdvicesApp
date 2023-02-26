package com.example.advicesapp.search.presentation

import com.example.advicesapp.R

interface Valid {

    fun isValid(query: String, block: (String) -> Unit)

    class Base(
        private val resources: ProvideResources,
        private val communication: SearchCommunication,
        private val validation: Validation,
    ) : Valid {

        override fun isValid(query: String, block: (String) -> Unit) =
            if (validation.isValid(query))
                block.invoke(validation.map(query))
            else
                communication.map(SearchUiState.Error(resources.string(R.string.invalid_input_message)))
    }
}