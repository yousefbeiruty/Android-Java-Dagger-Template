package com.it.yousef.dagger2ex.di.main;

import com.it.yousef.dagger2ex.network.auth.main.MainApi;
import com.it.yousef.dagger2ex.ui.auth.main.ui.gallery.GalleryAdapter;
import com.it.yousef.dagger2ex.utils.Constants;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {

    @MainScope
    @Provides
    static MainApi provideMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }


}
