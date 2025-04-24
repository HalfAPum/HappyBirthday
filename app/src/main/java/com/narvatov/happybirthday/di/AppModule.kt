package com.narvatov.happybirthday.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.narvatov.happybirthday.data.datastore.BirthdayDataStore
import com.narvatov.happybirthday.domain.SavePictureUriUseCase
import com.narvatov.happybirthday.ui.viewmodel.BirthdayViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { BirthdayDataStore(get()) }
    single { SavePictureUriUseCase(get(), get()) }
    viewModel { BirthdayViewModel(get(), get()) }
}