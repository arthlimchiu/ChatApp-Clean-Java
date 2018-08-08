package com.imakeanapp.chatapp.auth.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.imakeanapp.domain.user.model.User;
import com.imakeanapp.domain.user.usecase.LoginUseCase;
import com.imakeanapp.domain.user.usecase.SignUpUseCase;

import io.reactivex.Single;

public class AuthViewModel extends ViewModel {

    private SignUpUseCase signUp;
    private LoginUseCase login;

    public AuthViewModel(SignUpUseCase signUp, LoginUseCase login) {
        this.signUp = signUp;
        this.login = login;
    }

    public Single<User> signUp(String username, String password) {
        return signUp.execute(new User(username, password));
    }

    public Single<User> login(String username, String password) {
        return login.execute(new User(username, password));
    }
}
