package com.myproject.mvvmdemo.util

import kotlinx.coroutines.*

fun<T> lazyDeferred(block: suspend CoroutineScope.() -> T) : Lazy<Deferred<T>>{

    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }

}

// Deferred is basically a job with result