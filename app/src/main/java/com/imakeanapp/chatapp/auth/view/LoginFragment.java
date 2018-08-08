package com.imakeanapp.chatapp.auth.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.imakeanapp.chatapp.R;
import com.imakeanapp.chatapp.auth.viewmodel.AuthViewModel;
import com.imakeanapp.chatapp.auth.viewmodel.AuthViewModelFactory;
import com.imakeanapp.chatapp.core.Injector;
import com.imakeanapp.chatapp.util.InputUtil;
import com.imakeanapp.domain.user.model.User;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class LoginFragment extends Fragment {

    private AuthViewModelFactory factory = Injector.get().authViewModelFactory();
    private AuthViewModel viewModel;

    private MainActivityFragmentsListener callback;

    private EditText username;
    private EditText password;
    private TextView usernameError;
    private TextView passwordError;
    private TextView signUp;
    private Button login;

    private CompositeDisposable disposables;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disposables = new CompositeDisposable();

        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), factory).get(AuthViewModel.class);
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
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        usernameError = view.findViewById(R.id.username_error);
        passwordError = view.findViewById(R.id.password_error);
        signUp = view.findViewById(R.id.login);
        signUp.setPaintFlags(signUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onSignUpClick();
            }
        });
        login = view.findViewById(R.id.sign_up);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasErrors()) {
                    InputUtil.hideKeyboard(Objects.requireNonNull(getContext()), view);
                    disableLoginButton();
                    disposables.add(
                            viewModel.login(username.getText().toString(), password.getText().toString())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<User>() {
                                        @Override
                                        public void accept(User user) throws Exception {
                                            enableLoginButton();
                                            callback.onLoginSuccess(user.getUsername());
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            enableLoginButton();
                                            showUsernameError();
                                            showPasswordError();
                                            Log.e("LoginFragment", "Error: ", throwable);
                                        }
                                    })
                    );
                }
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        disposables.clear();
    }

    private boolean hasErrors() {
        boolean hasError = false;

        String usernameValue = username.getText().toString();
        if (usernameValue.isEmpty() || usernameValue.length() < 8) {
            hasError = true;
            showUsernameError();
        } else {
            hideUsernameError();
        }

        String passwordValue = password.getText().toString();
        if (passwordValue.isEmpty() || passwordValue.length() < 8) {
            hasError = true;
            showPasswordError();
        } else {
            hidePasswordError();
        }

        return hasError;
    }

    private void showUsernameError() {
        usernameError.setVisibility(View.VISIBLE);
    }

    private void hideUsernameError() {
        usernameError.setVisibility(View.GONE);
    }

    private void showPasswordError() {
        passwordError.setVisibility(View.VISIBLE);
    }

    private void hidePasswordError() {
        passwordError.setVisibility(View.GONE);
    }

    private void disableLoginButton() {
        login.setEnabled(false);
    }

    private void enableLoginButton() {
        login.setEnabled(true);
    }
}
