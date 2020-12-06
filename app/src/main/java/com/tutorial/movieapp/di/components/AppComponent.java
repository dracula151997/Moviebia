package com.tutorial.movieapp.di.components;

import android.app.Application;

import com.tutorial.movieapp.App;
import com.tutorial.movieapp.di.modules.ActivityFragmentModule;
import com.tutorial.movieapp.di.modules.ActivityModules;
import com.tutorial.movieapp.di.modules.ApiModule;
import com.tutorial.movieapp.di.modules.DBModule;
import com.tutorial.movieapp.di.modules.ViewModelModules;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {ActivityModules.class,
        AndroidInjectionModule.class,
        ActivityFragmentModule.class,
        ViewModelModules.class,
        AndroidSupportInjectionModule.class,
        DBModule.class,
        ApiModule.class})
public interface AppComponent
{


    @Component.Builder
    interface Builder
    {
        @BindsInstance
        Builder application(Application application);

        @BindsInstance
        Builder apiModule(ApiModule module);

        @BindsInstance
        Builder dbModule(DBModule module);

        AppComponent build();
    }

    void inject(App app);

}
