package com.myproject.mvvmdemo.ui.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myproject.mvvmdemo.data.repositories.QuoteRepository

@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory(
    private val repository: QuoteRepository
) : ViewModelProvider.NewInstanceFactory()  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(repository) as T
    }

}