package com.imakeanapp.chatapp.auth.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.imakeanapp.chatapp.R;

public class WelcomeFragment extends Fragment {

    private MainActivityFragmentsListener callback;

    private Button signUp;
    private Button login;
    private ImageView logo;

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
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        logo = view.findViewById(R.id.logo);
        signUp = view.findViewById(R.id.sign_up);
        login = view.findViewById(R.id.login);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onSignUpClick();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onLoginClick();
            }
        });



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ObjectAnimator animationX = ObjectAnimator.ofFloat(logo, "scaleX", 0f, 1f);
        ObjectAnimator animationY = ObjectAnimator.ofFloat(logo, "scaleY", 0f, 1f);
        animationX.setDuration(500);
        animationY.setDuration(500);
        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.playTogether(animationX, animationY);
        scaleUp.start();
    }
}
