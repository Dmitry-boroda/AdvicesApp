package com.example.advicesapp.search.presentation

interface Validation {
    fun isValid(query: String): Boolean
    fun map(data: String): String
}