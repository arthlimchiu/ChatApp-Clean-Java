package com.imakeanapp.chatapp.core;

public class Injector {

    private Injector() {
    }

    public static AppComponent get() {
        return App.get().component;
    }
}
