package com.it.yousef.dagger2ex.ui.auth.main;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.it.yousef.dagger2ex.BaseeActivity;
import com.it.yousef.dagger2ex.R;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends BaseeActivity implements NavigationView.
        OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private AppBarConfiguration mAppBarConfiguration;

    NavController navController;
    DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
         drawer = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController,drawer);
    }
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Log.d(TAG, "onNavigationItemSelected: ");
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                navController.navigate(R.id.nav_home);
                Log.d(TAG, "onNavigationItemSelected: home");

                break;

            case R.id.nav_gallery:
               if (isValidDestination(R.id.nav_gallery))
                    navController.navigate(R.id.nav_gallery);
                Log.d(TAG, "onNavigationItemSelected: gallery");

                break;
            case R.id.nav_slideshow:
              if (isValidDestination(R.id.nav_slideshow))
                navController.navigate(R.id.nav_slideshow);

                break;
        }
        menuItem.setCheckable(true);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isValidDestination(int destination){
        return destination!=Navigation.findNavController(this,R.id.nav_host_fragment).getCurrentDestination().getId();
    }


}
