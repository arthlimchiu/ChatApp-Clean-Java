package com.imakeanapp.chatapp.auth.view;

public interface MainActivityFragmentsListener {

    void onLoginClick();

    void onSignUpClick();

    void onLogoutClick();

    void onLoginSuccess(String username);

    void onSignUpSuccess(String username);
}
