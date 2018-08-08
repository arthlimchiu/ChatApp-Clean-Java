package com.imakeanapp.chatapp.messages.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.imakeanapp.domain.messages.model.Message;
import com.imakeanapp.domain.messages.usecase.GetMessagesUseCase;
import com.imakeanapp.domain.messages.usecase.SendMessageUseCase;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MessagesViewModel extends ViewModel {

    private GetMessagesUseCase getMessages;
    private SendMessageUseCase sendMessage;

    public MessagesViewModel(GetMessagesUseCase getMessages, SendMessageUseCase sendMessage) {
        this.getMessages = getMessages;
        this.sendMessage = sendMessage;
    }

    public Completable sendMessage(Message message) {
        return sendMessage.execute(message);
    }

    public Observable<List<Message>> getMessages() {
        return getMessages.execute();
    }
}
