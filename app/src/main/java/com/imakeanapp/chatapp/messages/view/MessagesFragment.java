package com.imakeanapp.chatapp.messages.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imakeanapp.chatapp.R;
import com.imakeanapp.chatapp.auth.view.MainActivityFragmentsListener;
import com.imakeanapp.chatapp.core.Injector;
import com.imakeanapp.chatapp.messages.viewmodel.MessagesViewModel;
import com.imakeanapp.chatapp.messages.viewmodel.MessagesViewModelFactory;
import com.imakeanapp.domain.messages.model.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MessagesFragment extends Fragment {
    public static String ARG_USERNAME = "arg_username";

    private MessagesViewModelFactory factory = Injector.get().messagesViewModelFactory();
    private MessagesViewModel viewModel;

    private MainActivityFragmentsListener callback;

    private RecyclerView messagesList;
    private MessagesAdapter adapter;

    private Button logOut;
    private Button sendMessage;
    private EditText message;

    private String username;

    private CompositeDisposable disposables;

    public static MessagesFragment newInstance(String username) {
        Bundle args = new Bundle();

        args.putString(ARG_USERNAME, username);

        MessagesFragment fragment = new MessagesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disposables = new CompositeDisposable();

        viewModel = ViewModelProviders.of(this, factory).get(MessagesViewModel.class);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (MainActivityFragmentsListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement MainActivityFragmentsListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);

        Bundle args = getArguments();
        username = Objects.requireNonNull(args).getString(ARG_USERNAME);

        sendMessage = view.findViewById(R.id.send);
        message = view.findViewById(R.id.message);
        logOut = view.findViewById(R.id.log_out);
        messagesList = view.findViewById(R.id.message_list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        messagesList.setLayoutManager(manager);
        adapter = new MessagesAdapter(username, new ArrayList<Message>());
        messagesList.setAdapter(adapter);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onLogoutClick();
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message chatMessage = new Message();
                chatMessage.setMessage(message.getText().toString());
                chatMessage.setSender(username);
                chatMessage.setSent(System.currentTimeMillis());
                message.setText("");
                disposables.add(
                        viewModel.sendMessage(chatMessage)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Action() {
                                    @Override
                                    public void run() throws Exception {

                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        showInternetError();
                                    }
                                })
                );
            }
        });

        addMessageBoxTextListener();

        disposables.add(
                viewModel.getMessages()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<List<Message>>() {
                            @Override
                            public void accept(List<Message> messages) throws Exception {
                                adapter.updateData(messages);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                showInternetError();
                            }
                        })
        );

        return view;
    }

    private void showInternetError() {
        Toast.makeText(getContext(), getString(R.string.internet_connection_error), Toast.LENGTH_SHORT).show();
    }

    private void addMessageBoxTextListener() {
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    sendMessage.setEnabled(true);
                } else {
                    sendMessage.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }
}
