package com.it.yousef.dagger2ex.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.it.yousef.dagger2ex.SessionManager;
import com.it.yousef.dagger2ex.models.User;
import com.it.yousef.dagger2ex.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    private SessionManager sessionManager;
    @Inject
    public AuthViewModel(AuthApi authApi,SessionManager sessionManager) {
        this.authApi=authApi;
        this.sessionManager=sessionManager;
        Log.d(TAG, "AuthViewModel: view model is working...");

    }

    public void authenticatWithID(int userId){

        Log.d(TAG, "authenticatWithID: attemed to login");

        sessionManager.authenticatedWithID(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {
        return LiveDataReactiveStreams
                .fromPublisher(authApi.getUser(userId)
                        //instead of calling an error(error happens)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser=new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })
                        .map(new Function<User, AuthResource<User>>() {
                            @Override
                            public AuthResource<User> apply(User user) throws Exception {
                                if (user.getId()==-1){
                                    return AuthResource.error("Cloud not authenticate",(User)null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })
                        .subscribeOn(Schedulers.io()));
    }
    public LiveData<AuthResource<User>>observeAuthState(){
        return sessionManager.getAuthUser();
    }
}
