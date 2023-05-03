package com.example.advicesapp.search.domain

import com.example.advicesapp.search.presentation.AdviceUi

data class Advice(
    private val id: Int,
    private val query: String,
    private val text: String,
    private val isFavorite: Boolean
) {
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, query, text, isFavorite)

    interface Mapper<T> {
        fun map(id: Int, query: String, text: String, isFavorite: Boolean): T
        class Base : Mapper<AdviceUi> {
            override fun map(id: Int, query: String, text: String, isFavorite: Boolean): AdviceUi {
                return AdviceUi(id, query, text, isFavorite)
            }
        }
    }
}