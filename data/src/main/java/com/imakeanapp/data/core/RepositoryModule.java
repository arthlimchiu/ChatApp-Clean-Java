package com.imakeanapp.data.core;

import com.imakeanapp.data.messages.MessagesRepositoryImpl;
import com.imakeanapp.data.user.AuthRepositoryImpl;
import com.imakeanapp.domain.messages.repository.MessagesRepository;
import com.imakeanapp.domain.user.repository.AuthRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public AuthRepository providesAuthRepository(AuthRepositoryImpl repository) {
        return repository;
    }

    @Provides
    public MessagesRepository providesMessagesRepository(MessagesRepositoryImpl repository) {
        return repository;
    }
}
