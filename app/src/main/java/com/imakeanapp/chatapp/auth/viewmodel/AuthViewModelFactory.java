package com.imakeanapp.chatapp.auth.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.imakeanapp.domain.user.usecase.LoginUseCase;
import com.imakeanapp.domain.user.usecase.SignUpUseCase;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings("UNCHECKED_CAST")
@Singleton
public class AuthViewModelFactory implements ViewModelProvider.Factory {

    private SignUpUseCase signUp;
    private LoginUseCase login;

    @Inject
    public AuthViewModelFactory(SignUpUseCase signUp, LoginUseCase login) {
        this.signUp = signUp;
        this.login = login;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(signUp, login);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}