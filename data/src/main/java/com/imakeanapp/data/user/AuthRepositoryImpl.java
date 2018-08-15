package com.imakeanapp.data.user;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.imakeanapp.domain.user.model.User;
import com.imakeanapp.domain.user.repository.AuthRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class AuthRepositoryImpl implements AuthRepository {

    private FirebaseFirestore db;

    @Inject
    public AuthRepositoryImpl(FirebaseFirestore db) {
        this.db = db;
    }

    @Override
    public Single<User> signup(final String username, final String password) {
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(final SingleEmitter<User> emitter) throws Exception {
                DocumentReference ref = db.collection("users").document(username);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                emitter.onError(new Exception("User exists"));
                            } else {
                                final User user = new User(username, password);
                                db.collection("users")
                                        .document(username)
                                        .set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                emitter.onSuccess(user);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                emitter.onError(e);
                                            }
                                        });
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<User> login(final String username, final String password) {
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(final SingleEmitter<User> emitter) throws Exception {
                DocumentReference ref = db.collection("users").document(username);
                ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                User user = document.toObject(User.class);
                                if (user != null && user.getPassword().contentEquals(password)) {
                                    emitter.onSuccess(user);
                                } else {
                                    emitter.onError(new Exception("Password incorrect"));
                                }
                            } else {
                                emitter.onError(new Exception("User doesn't exist"));
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io());
    }
}
