package com.it.yousef.dagger2ex.di.auth;

import androidx.lifecycle.ViewModel;

import com.it.yousef.dagger2ex.di.ViewModelKey;
import com.it.yousef.dagger2ex.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthView(AuthViewModel viewModel);

}

