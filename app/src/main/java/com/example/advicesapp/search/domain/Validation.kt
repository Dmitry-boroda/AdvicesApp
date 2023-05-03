package com.example.advicesapp.search.domain

interface Validation {
    fun isValid(query: String): Boolean
    fun map(data: String): String
}