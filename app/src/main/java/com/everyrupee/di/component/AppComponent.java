package com.everyrupee.di.component;

import android.app.Application;

import com.everyrupee.di.builder.ActivityBuilder;
import com.everyrupee.di.module.AppModule;
import com.everyrupee.AppController;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(AppController app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
