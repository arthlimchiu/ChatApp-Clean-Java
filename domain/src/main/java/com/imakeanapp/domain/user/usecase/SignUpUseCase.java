package com.imakeanapp.domain.user.usecase;

import com.imakeanapp.domain.core.SingleWithParamUseCase;
import com.imakeanapp.domain.user.model.User;
import com.imakeanapp.domain.user.repository.AuthRepository;

import io.reactivex.Single;

public class SignUpUseCase implements SingleWithParamUseCase<User, User> {

    private AuthRepository repository;

    public SignUpUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<User> execute(User user) {
        return repository.signup(user.getUsername(), user.getPassword());
    }
}
