package com.myproject.mvvmdemo.ui.quotes

import androidx.lifecycle.ViewModel
import com.myproject.mvvmdemo.data.repositories.QuoteRepository
import com.myproject.mvvmdemo.util.lazyDeferred

class QuotesViewModel(repository: QuoteRepository) : ViewModel() {

    val quotes by lazyDeferred {

        repository.getQuotes()

    }

}
