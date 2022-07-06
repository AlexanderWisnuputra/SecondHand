package com.example.secondhand

import android.app.Application
import androidx.room.Room
import com.example.secondhand.dao.UserDao
import com.example.secondhand.dao.UserDatabase
import com.example.secondhand.dao.UserViewModel
import com.example.secondhand.sellerProduct.SPViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userDB= module {
    fun provideDataBase(application: Application): UserDatabase {
        return Room.databaseBuilder(application, UserDatabase::class.java, "item_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(database: UserDatabase): UserDao {
        return database.daoLogin()
    }
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}

val roomVModel = module {
    viewModel {
        UserViewModel(get())
    }
}
