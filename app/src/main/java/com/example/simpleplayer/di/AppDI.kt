package com.example.simpleplayer.di

import com.example.simpleplayer.PlayerViewModel
import com.example.simpleplayer.player.PlayerController
import com.example.simpleplayer.player.PlayerControllerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        PlayerViewModel(get())
    }
    factory <PlayerController> {
        PlayerControllerImpl(androidContext())
    }
}