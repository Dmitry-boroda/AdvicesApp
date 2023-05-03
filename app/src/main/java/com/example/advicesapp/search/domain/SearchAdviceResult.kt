package com.example.advicesapp.search.domain

interface SearchAdviceResult {
    interface Mapper<T> {
        fun map(list: List<Advice>): T
        fun map(exception: DomainException): T
    }

    fun <T> map(mapper: Mapper<T>): T

    data class Success(private val list: List<Advice> = emptyList()) : SearchAdviceResult {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list)
    }

    data class Error(private val exception: DomainException) : SearchAdviceResult {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(exception)
    }
}