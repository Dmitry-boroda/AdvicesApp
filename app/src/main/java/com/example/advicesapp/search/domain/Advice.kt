package com.example.advicesapp.search.domain

import com.example.advicesapp.search.presentation.AdviceUi

data class Advice(
    private val id: Int,
    private val text: String,
    private val isFavorite: Boolean
) {
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, text, isFavorite)

    interface Mapper<T> {
        fun map(id: Int, text: String, isFavorite: Boolean): T
        class Base : Mapper<AdviceUi> {
            override fun map(id: Int, text: String, isFavorite: Boolean): AdviceUi {
                return AdviceUi(id, text, isFavorite)
            }
        }
    }
}