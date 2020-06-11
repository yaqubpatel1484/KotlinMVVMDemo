package com.myproject.mvvmdemo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.myproject.mvvmdemo.data.entities.Quote
import com.myproject.mvvmdemo.data.entities.QuoteDao
import com.myproject.mvvmdemo.data.entities.User
import com.myproject.mvvmdemo.data.entities.UserDao


@Database(
    entities = [User::class,Quote::class],
    version = 1
)


abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao
    abstract fun getQuoteDao(): QuoteDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            /*  'instance ?:' it checks instance is null or not, if it is not null it returns instance immediately
                  otherwise execute further statements
            */
            instance ?: buildDatabase(context).also {
                instance = it
            }

        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db")
                .fallbackToDestructiveMigration()
                .build()

    }
}