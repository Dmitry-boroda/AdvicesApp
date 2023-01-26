package com.example.advicesapp.search.domain

abstract class DomainException: IllegalStateException(){
    class NoInternetConnection: DomainException()
    class ServiceUnavailable: DomainException()
}