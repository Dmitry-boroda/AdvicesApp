package com.example.advicesapp.search.data

import com.example.advicesapp.R
import com.example.advicesapp.search.domain.DomainException
import com.example.advicesapp.search.presentation.ProvideResources

interface HandelError<T> {
    fun handle(exception: DomainException): T
    class Base(private val resources: ProvideResources) : HandelError<String> {
        override fun handle(exception: DomainException) = resources.string(
            when (exception) {
                is DomainException.NoInternetConnection -> R.string.no_internet
                else -> R.string.service_error_message
            }
        )
    }
}
