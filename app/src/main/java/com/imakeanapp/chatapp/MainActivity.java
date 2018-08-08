package com.imakeanapp.chatapp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.imakeanapp.chatapp.auth.view.LoginFragment;
import com.imakeanapp.chatapp.auth.view.MainActivityFragmentsListener;
import com.imakeanapp.chatapp.auth.view.SignUpFragment;
import com.imakeanapp.chatapp.auth.view.WelcomeFragment;
import com.imakeanapp.chatapp.messages.view.MessagesFragment;

public class MainActivity extends AppCompatActivity implements MainActivityFragmentsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showWelcomeFragment();
    }

    @Override
    public void onLoginClick() {
        showLoginFragment();
    }

    @Override
    public void onSignUpClick() {
        showSignUpFragment();
    }

    @Override
    public void onLogoutClick() {
        showSignUpFragment();
    }

    @Override
    public void onLoginSuccess(String username) {
        showChatFragment(username);
    }

    @Override
    public void onSignUpSuccess(String username) {
        showChatFragment(username);
    }

    private void showWelcomeFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new WelcomeFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showLoginFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                        R.animator.slide_in_from_left, R.animator.fade_out)
                .replace(R.id.fragment_container, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showSignUpFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                        R.animator.slide_in_from_left, R.animator.fade_out)
                .replace(R.id.fragment_container, new SignUpFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showChatFragment(String username) {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_from_right, R.animator.slide_out_to_left,
                        R.animator.slide_in_from_left, R.animator.fade_out)
                .replace(R.id.fragment_container, MessagesFragment.newInstance(username), "MessagesFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentByTag("MessagesFragment") != null) {
            showSignUpFragment();
            return;
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
