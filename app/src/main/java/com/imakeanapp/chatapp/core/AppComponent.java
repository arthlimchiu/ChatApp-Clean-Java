package com.imakeanapp.chatapp.core;

import com.google.firebase.firestore.FirebaseFirestore;
import com.imakeanapp.chatapp.auth.viewmodel.AuthViewModelFactory;
import com.imakeanapp.chatapp.messages.viewmodel.MessagesViewModelFactory;
import com.imakeanapp.data.core.DatabaseModule;
import com.imakeanapp.data.core.RepositoryModule;
import com.imakeanapp.domain.messages.repository.MessagesRepository;
import com.imakeanapp.domain.user.repository.AuthRepository;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        ViewModelModule.class,
        DatabaseModule.class,
        RepositoryModule.class
})
@Singleton
public interface AppComponent {

    AuthRepository authRepository();

    MessagesRepository messagesRepository();

    AuthViewModelFactory authViewModelFactory();

    MessagesViewModelFactory messagesViewModelFactory();

    FirebaseFirestore firebaseFirestore();
}
