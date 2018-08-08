package com.imakeanapp.domain.messages.usecase;

import com.imakeanapp.domain.core.ObservableUseCase;
import com.imakeanapp.domain.messages.model.Message;
import com.imakeanapp.domain.messages.repository.MessagesRepository;

import java.util.List;

import io.reactivex.Observable;

public class GetMessagesUseCase implements ObservableUseCase<List<Message>> {

    private MessagesRepository repository;

    public GetMessagesUseCase(MessagesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<List<Message>> execute() {
        return repository.getMessages();
    }
}
