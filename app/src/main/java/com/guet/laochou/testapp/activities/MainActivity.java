package com.guet.laochou.testapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.navigation.NavigationView;
import com.guet.laochou.testapp.R;
import com.guet.laochou.testapp.utils.UMainListAdapter;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private ImageView userHead_openDrawer;
    private ImageView userHead_inDrawer;
    private ImageView likeBtn_L,likeBtn_R,download_L,download_R;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        drawer = findViewById(R.id.drawer_layout);
        userHead_openDrawer = findViewById(R.id.main_iv_userHead);
        NavigationView navigationView = findViewById(R.id.nav_view);
        userHead_inDrawer = navigationView.getHeaderView(0).findViewById(R.id.main_iv_userHeadInDrawer);

        userHead_openDrawer.setOnClickListener(this);
        userHead_inDrawer.setOnClickListener(this);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_likes, R.id.nav_upload)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.cnav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_iv_userHead:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.main_iv_userHeadInDrawer:
                Log.d("TESTTAG", "userHead_inDrawer click");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.main_list_card1_iv_likeBtn:
                Log.d("TESTTAG", "userHead_inDrawer click");
                break;
            case R.id.main_list_card1_iv_download:
                Log.d("TESTTAG", "userHead_inDrawer click");
                break;
            case R.id.main_list_card2_iv_likeBtn:
                break;
            case R.id.main_list_card2_iv_download:
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.cnav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}