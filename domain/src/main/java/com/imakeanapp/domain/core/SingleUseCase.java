package com.imakeanapp.domain.core;

import io.reactivex.Single;

public interface SingleUseCase<T> {

    Single<T> execute();
}
