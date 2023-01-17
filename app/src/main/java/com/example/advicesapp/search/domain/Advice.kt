package com.example.advicesapp.search.domain

import com.example.advicesapp.search.presentation.Mapper

data class Advice(
    private val id: Int,
    private val text: String,
    private val isFavorite: Boolean
) : Mapper<Boolean, Advice> {
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, text, isFavorite)

    interface Mapper<T> {
        fun map(id: Int, text: String, isFavorite: Boolean): T
    }

    override fun map(source: Advice): Boolean = source.id == id
}