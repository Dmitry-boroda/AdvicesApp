package com.example.advicesapp.search.presentation

import android.view.View
import com.example.advicesapp.databinding.SearchFragmentBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

interface UiState {

    fun apply(
        inputLayout: TextInputLayout,
        textInputEditText: TextInputEditText,
        binding: SearchFragmentBinding
    )

    class ProgressState(private val state: Int) : UiState {
        override fun apply(
            inputLayout: TextInputLayout,
            textInputEditText: TextInputEditText,
            binding: SearchFragmentBinding
        ) {
            binding.progressBar.visibility = state
        }
    }

    class Success : UiState {
        override fun apply(
            inputLayout: TextInputLayout,
            textInputEditText: TextInputEditText,
            binding: SearchFragmentBinding
        ) {
            binding.progressBar.visibility = View.GONE
            textInputEditText.setText("")
        }
    }

    abstract class AbstractError(
        private val message: String,
        private val errorEnable: Boolean
    ) : UiState {
        override fun apply(
            inputLayout: TextInputLayout,
            textInputEditText: TextInputEditText,
            binding: SearchFragmentBinding
        ) = with(inputLayout) {
            binding.progressBar.visibility = View.GONE
            isErrorEnabled = errorEnable
            error = message
        }
    }

    data class ShowError(private val message: String) : AbstractError(message, true)

    class ClearError : AbstractError("", false)
}