package com.imakeanapp.chatapp.core;

import android.app.Application;

import com.imakeanapp.data.core.DatabaseModule;
import com.imakeanapp.data.core.RepositoryModule;

public class App extends Application {

    private static App INSTANCE = null;

    public AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        component = DaggerAppComponent.builder()
                .databaseModule(new DatabaseModule())
                .repositoryModule(new RepositoryModule())
                .viewModelModule(new ViewModelModule())
                .build();
    }

    public static App get() {
        return INSTANCE;
    }
}
