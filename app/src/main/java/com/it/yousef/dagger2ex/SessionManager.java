package com.it.yousef.dagger2ex;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.it.yousef.dagger2ex.models.User;
import com.it.yousef.dagger2ex.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    private MediatorLiveData<AuthResource<User>> cashedUser=new MediatorLiveData<>();

    @Inject
    public SessionManager() {
    }

    public void authenticatedWithID(final LiveData<AuthResource<User>> source){
        if (cashedUser!=null){
            cashedUser.setValue(AuthResource.loading((User) null));
            cashedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cashedUser.setValue(userAuthResource);
                    cashedUser.removeSource(source);
                }
            });
        }else {
            Log.d(TAG, "authenticatedWithID: previous session detected retriving user from cache");
        }
    }

    public void logOut(){
        Log.d(TAG, "logOut: loging out...");
        cashedUser.setValue(AuthResource.<User>logout());
    }

    public LiveData<AuthResource<User>> getAuthUser(){
        return cashedUser;
    }
}

