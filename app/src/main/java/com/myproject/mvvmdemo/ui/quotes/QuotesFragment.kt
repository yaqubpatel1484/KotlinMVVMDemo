package com.myproject.mvvmdemo.ui.quotes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.myproject.mvvmdemo.R
import com.myproject.mvvmdemo.data.entities.Quote
import com.myproject.mvvmdemo.util.Coroutines
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    private val factory: QuotesViewModelFactory by instance<QuotesViewModelFactory>()


    private lateinit var viewModel: QuotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)

        bindUI()


    }

    private fun bindUI() = Coroutines.main {
        quote_pregressbar.show()
        val quotes = viewModel.quotes.await()
        quotes.observe(viewLifecycleOwner, Observer {
            quote_pregressbar.hide()
            initRecyclerView(it.toQuoteItem())  // we have list of quote and to display it in recyclerView we need list of quoteItem bcoz we are using groupie library here

        })
    }

    private fun initRecyclerView(quoteItem: List<Quoteitem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(quoteItem)
        }

        recyclerQuote.apply {

            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }


    // here we are writing extension function of List<Quote> class to get list of QuoteItem
    private fun List<Quote>.toQuoteItem(): List<Quoteitem> {
        return this.map {
            Quoteitem(it)
        }
    }


}
