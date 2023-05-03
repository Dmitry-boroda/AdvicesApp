package com.example.advicesapp.search.presentation

import android.view.View
import com.example.advicesapp.databinding.SearchFragmentBinding

interface SearchUiState {

    fun show(binding: SearchFragmentBinding, adapter: Mapper.Unit<List<AdviceUi>>)


    data class Success(private val list: List<AdviceUi>) : SearchUiState {
        override fun show(binding: SearchFragmentBinding, adapter: Mapper.Unit<List<AdviceUi>>) {
            binding.progressBar.visibility
            adapter.map(list)
        }
    }

    data class Error(private val message: String) : SearchUiState {
        override fun show(binding: SearchFragmentBinding, adapter: Mapper.Unit<List<AdviceUi>>) {

        }
    }

    object Progress : SearchUiState {
        override fun show(binding: SearchFragmentBinding, adapter: Mapper.Unit<List<AdviceUi>>) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }
}