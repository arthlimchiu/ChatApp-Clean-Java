package com.imakeanapp.chatapp.messages.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.imakeanapp.domain.messages.usecase.GetMessagesUseCase;
import com.imakeanapp.domain.messages.usecase.SendMessageUseCase;

import javax.inject.Inject;
import javax.inject.Singleton;

@SuppressWarnings("UNCHECKED_CAST")
@Singleton
public class MessagesViewModelFactory implements ViewModelProvider.Factory {

    private GetMessagesUseCase getMessages;
    private SendMessageUseCase sendMessage;

    @Inject
    public MessagesViewModelFactory(GetMessagesUseCase getMessages, SendMessageUseCase sendMessage) {
        this.getMessages = getMessages;
        this.sendMessage = sendMessage;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MessagesViewModel.class)) {
            return (T) new MessagesViewModel(getMessages, sendMessage);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
