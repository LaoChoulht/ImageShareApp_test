package com.guet.laochou.testapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.guet.laochou.testapp.activities.R;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
//    private SharedPreferences spFile;

    //权限
    private final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //判断是否获得权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //判断是否弹出说明
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
                }
            }
        }
        Log.d("TESTTAG", "main onCreate: ");
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