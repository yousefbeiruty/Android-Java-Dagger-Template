package com.it.yousef.dagger2ex.di.main;

import androidx.lifecycle.ViewModel;

import com.it.yousef.dagger2ex.di.ViewModelKey;
import com.it.yousef.dagger2ex.ui.auth.main.ui.gallery.GalleryViewModel;
import com.it.yousef.dagger2ex.ui.auth.main.ui.home.HomeViewModel;
import com.it.yousef.dagger2ex.ui.auth.main.ui.slideshow.SlideshowViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    public abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GalleryViewModel.class)
    public abstract ViewModel bindGalleryViewModel(GalleryViewModel galleryViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(SlideshowViewModel.class)
    public abstract ViewModel bindSlideshowViewModel(SlideshowViewModel slideshowViewModel);
}
