package com.example.advicesapp.search.domain

import com.example.advicesapp.search.data.ServiceException

sealed class SearchAdviceResult {
    interface Mapper<T>{
        fun map(list: List<Advice>): T
        fun map(exception: ServiceException): T
    }
    abstract fun <T> map(mapper: Mapper<T>): T

    data class Success(private val list: List<Advice> = emptyList()) : SearchAdviceResult() {
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list)

    }

    data class Error(private val exception: ServiceException): SearchAdviceResult(){
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(exception)
    }
}