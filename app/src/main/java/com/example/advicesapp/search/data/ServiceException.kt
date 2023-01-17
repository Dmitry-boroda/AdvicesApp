package com.example.advicesapp.search.data

import com.example.advicesapp.search.domain.NoInternetConnectionException
import com.example.advicesapp.search.domain.ServiceUnavailableException
import java.net.UnknownHostException

class ServiceException: HandelError<Exception> {
    override fun handle(exception: Exception): Exception =
        when(exception){
            is UnknownHostException -> NoInternetConnectionException()
            else -> ServiceUnavailableException()
        }
}