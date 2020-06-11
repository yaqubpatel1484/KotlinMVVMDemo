package com.myproject.mvvmdemo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object Coroutines {

    fun main(work: suspend (() -> Unit)) =

        CoroutineScope(Dispatchers.Main).launch {
            work()
        }

    // here we are executing it in the main thread


    fun io(work: suspend (() -> Unit)) =

        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    // here we are executing it in the io thread

}