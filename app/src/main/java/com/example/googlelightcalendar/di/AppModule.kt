package com.example.googlelightcalendar.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.googlelightcalendar.auth.AuthorizationState
import com.example.googlelightcalendar.auth.OAuthStateHandler
import com.example.googlelightcalendar.auth.OauthClientImp
import com.example.googlelightcalendar.core.GoogleTokenManagerImpl
import com.example.googlelightcalendar.core.TokenManager
import com.example.googlelightcalendar.core.registration.UserRegistrationCache
import com.example.googlelightcalendar.core.registration.UserRegistrationCacheImpl
import com.example.googlelightcalendar.navigation.components.AuthNavManager
import com.example.googlelightcalendar.navigation.components.MainScreenNavManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesTokenManager(
        dataStore: DataStore<Preferences>
    ): TokenManager {
        return GoogleTokenManagerImpl(
            dataStore
        )
    }

    @Provides
    @Singleton
    fun providesGoogleAuthState(
        @ApplicationContext context: Context,
    ): AuthorizationState {
        return OAuthStateHandler(context)
    }

    @Provides
    @Singleton
    fun providesGoogleAuthClient(
        @ApplicationContext context: Context,
        tokenManager: GoogleTokenManagerImpl,
        authState: OAuthStateHandler,
    ): Lazy<OauthClientImp> {
        return lazy {
            OauthClientImp(
                context = context,
                tokenManager = tokenManager,
                oauthState = authState,
            )
        }
    }

    @Provides
    @Singleton
    fun providesAuthNavigationManager(
        externalScope: CoroutineScope
    ): AuthNavManager = AuthNavManager(
        externalScope
    )

    @Provides
    @Singleton
    fun providesMainScreenNavigationManager(
        authNavManager: AuthNavManager,
        externalScope: CoroutineScope
    ): MainScreenNavManager = MainScreenNavManager(
        authNavManager,
        externalScope
    )

    @Provides
    @Singleton
    fun provideRegistrationCache(): UserRegistrationCache = UserRegistrationCacheImpl()
}