package com.it.yousef.dagger2ex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.it.yousef.dagger2ex.models.User;
import com.it.yousef.dagger2ex.ui.auth.AuthActivity;
import com.it.yousef.dagger2ex.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseeActivity extends DaggerAppCompatActivity {


    private static final String TAG = "BaseeActivity";


    @Inject
    public SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(this, "BaseeActivity", Toast.LENGTH_SHORT).show();
        subscribeObservers();
    }

    private void subscribeObservers(){

        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {
                    switch (userAuthResource.status) {
                        case ERROR:

                            Log.d(TAG, "onChanged: LogIn error "+userAuthResource.message);

                            Toast.makeText(BaseeActivity.this, userAuthResource.message, Toast.LENGTH_SHORT).show();

                            break;

                        case NOT_AUTHENTICATED:
                            navLoginScreen();
                            Log.d(TAG, "onChanged: LogIn failed");


                            break;

                        case LOADING:

                            break;

                        case AUTHENTICATED:

                            Log.d(TAG, "onChanged: LogIn success "+userAuthResource.data.getEmail());
                            break;


                    }
                }
            }
        });

    }

    private void navLoginScreen(){
        Intent intent=new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
