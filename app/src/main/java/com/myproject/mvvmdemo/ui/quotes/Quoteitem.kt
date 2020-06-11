package com.myproject.mvvmdemo.ui.quotes

import com.myproject.mvvmdemo.R
import com.myproject.mvvmdemo.data.entities.Quote
import com.myproject.mvvmdemo.databinding.QuotesItemViewBinding
import com.xwray.groupie.databinding.BindableItem

class Quoteitem(
    private val quote: Quote
) : BindableItem<QuotesItemViewBinding>() {
    override fun getLayout() = R.layout.quotes_item_view


    override fun bind(viewBinding: QuotesItemViewBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}