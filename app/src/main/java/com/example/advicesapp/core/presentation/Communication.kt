package com.example.advicesapp.core.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.advicesapp.search.presentation.Mapper

interface Communication<T : Any> {
    interface Observe<T : Any> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>) = Unit
    }

    interface Update<T : Any> : Mapper.Unit<T>

    interface Mutable<T : Any> : Observe<T>, Update<T>

    abstract class Abstract<T : Any>(
        protected val mutableLiveData: MutableLiveData<T> = SingleLiveEvent()
    ) : Mutable<T> {
        override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
            mutableLiveData.observe(owner, observer)

        override fun map(source: T) {
            mutableLiveData.value = source
        }
    }
}
