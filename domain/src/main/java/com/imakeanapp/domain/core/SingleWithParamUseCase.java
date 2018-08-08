package com.imakeanapp.domain.core;

import io.reactivex.Single;

public interface SingleWithParamUseCase<T, R> {

    Single<R> execute(T t);
}
