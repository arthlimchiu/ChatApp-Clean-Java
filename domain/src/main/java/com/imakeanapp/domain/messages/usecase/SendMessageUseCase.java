package com.imakeanapp.domain.messages.usecase;

import com.imakeanapp.domain.core.CompletableWithParamUseCase;
import com.imakeanapp.domain.messages.model.Message;
import com.imakeanapp.domain.messages.repository.MessagesRepository;

import io.reactivex.Completable;

public class SendMessageUseCase implements CompletableWithParamUseCase<Message> {

    private MessagesRepository repository;

    public SendMessageUseCase(MessagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Completable execute(Message message) {
        return repository.sendMessage(message);
    }
}
