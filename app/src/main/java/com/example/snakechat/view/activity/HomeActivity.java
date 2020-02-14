package com.example.snakechat.view.activity;

import android.app.SearchManager;
import android.os.Build;
import android.os.Bundle;

import com.example.snakechat.R;
import com.example.snakechat.data.local.Saveddata;
import com.example.snakechat.view.fragment.ui.home.UserProfileFragment;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.SearchView;

import static com.example.snakechat.data.local.HelperMethod.ReplaceFragment;
import static com.example.snakechat.data.local.SharedPreferencesManger.SaveData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;

public class HomeActivity extends BaseActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Saveddata saveddata;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        saveddata=new Saveddata(HomeActivity.this);
        saveddata.Read_data();

         toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setTitle(String title)
    {
     toolbar.setTitle(title);
     toolbar.setBackgroundColor(getColor(R.color.dark_green));
     toolbar.setTitleTextColor(getColor(R.color.white));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            setSharedPreferences(HomeActivity.this);
            SaveData(HomeActivity.this,"username" , query);
            ReplaceFragment( getSupportFragmentManager(), new UserProfileFragment(), R.id.nav_host_fragment
            , null, "profile");
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });


        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    @Override
//    public void onBackPressed() {
//        finish();
//    }
}
