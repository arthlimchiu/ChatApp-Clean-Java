package com.imakeanapp.chatapp.core;

import com.imakeanapp.chatapp.auth.viewmodel.AuthViewModelFactory;
import com.imakeanapp.chatapp.messages.viewmodel.MessagesViewModelFactory;
import com.imakeanapp.data.messages.MessagesRepositoryImpl;
import com.imakeanapp.data.user.AuthRepositoryImpl;
import com.imakeanapp.domain.messages.usecase.GetMessagesUseCase;
import com.imakeanapp.domain.messages.usecase.SendMessageUseCase;
import com.imakeanapp.domain.user.usecase.LoginUseCase;
import com.imakeanapp.domain.user.usecase.SignUpUseCase;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModelModule {

    @Provides
    public AuthViewModelFactory providesAuthViewModelFactory(AuthRepositoryImpl repository) {
        return new AuthViewModelFactory(
                new SignUpUseCase(repository),
                new LoginUseCase(repository)
        );
    }

    @Provides
    public MessagesViewModelFactory providesViewModelFactory(MessagesRepositoryImpl repository) {
        return new MessagesViewModelFactory(
                new GetMessagesUseCase(repository),
                new SendMessageUseCase(repository)
        );
    }
}
