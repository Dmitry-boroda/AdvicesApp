package com.example.advicesapp.search.presentation

interface Mapper<R, S> {
    fun map(source: S): R

    interface Unit<S> : Mapper<kotlin.Unit, S>
}