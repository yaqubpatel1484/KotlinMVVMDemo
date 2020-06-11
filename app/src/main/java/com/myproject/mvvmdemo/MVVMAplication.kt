package com.myproject.mvvmdemo

import android.app.Application
import com.myproject.mvvmdemo.data.db.AppDatabase
import com.myproject.mvvmdemo.data.network.MyApi
import com.myproject.mvvmdemo.data.network.NetworkConnectionInterceptor
import com.myproject.mvvmdemo.data.preferences.PreferenceProvider
import com.myproject.mvvmdemo.data.repositories.QuoteRepository
import com.myproject.mvvmdemo.data.repositories.UserRepository
import com.myproject.mvvmdemo.ui.auth.AuthViewModelFactory
import com.myproject.mvvmdemo.ui.home.profile.ProfileViewModelFactory
import com.myproject.mvvmdemo.ui.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MVVMApplication)) // Importing module

// in kodein we can pass 'instance()' as parameter instead of any object, we already binned
        // bind all objects that we need

        bind() from singleton { NetworkConnectionInterceptor(instance()) } // here instance() passing as parameter for context
        bind() from singleton { MyApi(instance()) } // MyApi get NetworkConnectionInterceptor as a parameter and here we can pass 'instance()' because we already bind NetworkConnectionInterceptor class in kodein
        bind() from singleton { AppDatabase(instance()) } // here also instance() passing as parameter replacement of context
        bind() from singleton { PreferenceProvider(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) } // UserRepository takes two parameter MyApi() and AppDatabase() both are already bind so we can pass 'instance()' replacement of both
        bind() from singleton { QuoteRepository(instance(), instance(),instance()) } // QuoteRepository takes three parameter MyApi() and AppDatabase() and PreferenceProvider() all are already bind so we can pass 'instance()' replacement of all

        bind() from provider { AuthViewModelFactory(instance()) } // here instance() passing as parameter for UserRepository()
        bind() from provider { ProfileViewModelFactory(instance()) } // here instance() passing as parameter for UserRepository()
        bind() from provider { QuotesViewModelFactory(instance()) } // here instance() passing as parameter for QuotesRepository()
        /*
        'bind() from singleton' means we are binding object as a singleton object
        and if we don't need singleton object then we can bind as provider i.e 'bind() from provider'
         */


    }

}