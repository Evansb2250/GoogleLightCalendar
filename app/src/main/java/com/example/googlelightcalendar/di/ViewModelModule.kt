package com.example.googlelightcalendar.di

import com.example.googlelightcalendar.repo.UserRepository
import com.example.googlelightcalendar.core.viewmodels.login.LoginViewModel
import com.example.googlelightcalendar.navigation.components.NavigationManger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun providesLoginViewModel(
        userRepository: UserRepository,
        navigationManger: NavigationManger,
    ): LoginViewModel {
        return LoginViewModel(
            navigationManager = navigationManger,
            userRepository = userRepository,
        )
    }
}