package com.example.advicesapp.search.presentation

data class AdviceUi(
    private val id: Int,
    private val text: String,
    private val isFavorite: Boolean
) : Mapper<Boolean, AdviceUi> {
    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, text, isFavorite)

    interface Mapper<T> {
        fun map(id: Int, text: String, isFavorite: Boolean): T
    }

    override fun map(source: AdviceUi): Boolean = source.id == id
}