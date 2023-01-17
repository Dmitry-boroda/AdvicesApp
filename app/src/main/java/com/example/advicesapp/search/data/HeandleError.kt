package com.example.advicesapp.search.data

interface HandelError<T>{
    fun handle(exception: Exception): T
}