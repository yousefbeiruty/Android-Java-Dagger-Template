package com.it.yousef.dagger2ex.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.RequestManager;
import com.it.yousef.dagger2ex.R;
import com.it.yousef.dagger2ex.models.User;
import com.it.yousef.dagger2ex.ui.auth.main.MainActivity;
import com.it.yousef.dagger2ex.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    private AuthViewModel viewModel;

    private EditText user_id;

    private ProgressBar progressBar;

    //inject
    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    //views
    ImageView imageView;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        btn=findViewById(R.id.btn);
        user_id=findViewById(R.id.et);
        imageView=findViewById(R.id.img);
        viewModel= ViewModelProviders.of(this,providerFactory).get(AuthViewModel.class);
        setLogo();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user_id.getText().toString().isEmpty())
                subscribeObservers();
            }
        });
    }

    private void subscribeObservers() {
        viewModel.authenticatWithID(Integer.parseInt(user_id.getText().toString()));
        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource!=null){
                    switch (userAuthResource.status){
                        case LOADING:
                            Log.d(TAG, "onChanged: loading...");
                            break;

                        case AUTHENTICATED:

                            onLogInSuccess();
                            break;
                        case ERROR:
                            Log.d(TAG, "onChanged: error= "+userAuthResource.message);
                            break;
                        case NOT_AUTHENTICATED:


                            break;
                    }
                }
            }
        });
    }

    private void onLogInSuccess() {
        Toast.makeText(AuthActivity.this,"yrdddd", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void setLogo() {
        requestManager.load(logo)
                .into(imageView);
    }

}
