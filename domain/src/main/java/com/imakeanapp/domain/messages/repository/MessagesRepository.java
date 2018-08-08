package com.imakeanapp.domain.messages.repository;

import com.imakeanapp.domain.messages.model.Message;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface MessagesRepository {

    Completable sendMessage(Message message);

    Observable<List<Message>> getMessages();
}
