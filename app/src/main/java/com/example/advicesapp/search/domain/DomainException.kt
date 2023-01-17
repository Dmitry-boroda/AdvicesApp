package com.example.advicesapp.search.domain

abstract class DomainException: IllegalStateException()
class NoInternetConnectionException: Exception()
class ServiceUnavailableException: Exception()