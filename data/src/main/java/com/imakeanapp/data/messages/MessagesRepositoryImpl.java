package com.imakeanapp.data.messages;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.imakeanapp.domain.messages.model.Message;
import com.imakeanapp.domain.messages.repository.MessagesRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class MessagesRepositoryImpl implements MessagesRepository {

    private FirebaseFirestore db;

    @Inject
    public MessagesRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public Completable sendMessage(final Message message) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(final CompletableEmitter emitter) throws Exception {
                db.collection("messages")
                        .add(message)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                emitter.onComplete();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                emitter.onError(e);
                            }
                        });
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<List<Message>> getMessages() {
        return Observable.create(new ObservableOnSubscribe<List<Message>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Message>> emitter) throws Exception {
                db.collection("messages")
                        .orderBy("sent", Query.Direction.DESCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    emitter.onError(e);
                                    return;
                                }

                                List<Message> messages = new ArrayList<>();
                                if (snapshots != null) {
                                    for (QueryDocumentSnapshot doc : snapshots) {
                                        messages.add(
                                                new Message(
                                                        doc.getString("message"),
                                                        doc.getString("sender"),
                                                        doc.getLong("sent")
                                                )
                                        );
                                    }
                                }

                                emitter.onNext(messages);
                            }
                        });
            }
        }).subscribeOn(Schedulers.io());
    }
}
