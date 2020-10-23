package com.it.yousef.dagger2ex.di.main;


import com.it.yousef.dagger2ex.ui.auth.main.ui.gallery.GalleryFragment;
import com.it.yousef.dagger2ex.ui.auth.main.ui.home.HomeFragment;
import com.it.yousef.dagger2ex.ui.auth.main.ui.slideshow.SlideshowFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentsBuilderModules {


    @ContributesAndroidInjector
    abstract GalleryFragment contributeGalleryFragment();

    @ContributesAndroidInjector
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector
    abstract SlideshowFragment contributeSlideshowFragment();
}
