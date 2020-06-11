package com.myproject.mvvmdemo.data.network.responses

import com.myproject.mvvmdemo.data.entities.Quote

data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)