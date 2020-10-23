package com.it.yousef.dagger2ex.di;
import com.it.yousef.dagger2ex.di.auth.AuthModule;
import com.it.yousef.dagger2ex.di.auth.AuthScope;
import com.it.yousef.dagger2ex.di.auth.AuthViewModelsModule;
import com.it.yousef.dagger2ex.di.main.MainFragmentsBuilderModules;
import com.it.yousef.dagger2ex.di.main.MainModule;
import com.it.yousef.dagger2ex.di.main.MainScope;
import com.it.yousef.dagger2ex.di.main.MainViewModelsModule;
import com.it.yousef.dagger2ex.ui.auth.AuthActivity;
import com.it.yousef.dagger2ex.ui.auth.main.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;


@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(modules = {AuthModule.class, AuthViewModelsModule.class})
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {MainFragmentsBuilderModules.class
    , MainViewModelsModule.class, MainModule.class})
    abstract MainActivity contributeMainActivity();
}
