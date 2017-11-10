package com.example.oims001.ccfbleizing;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NavigationMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment = null;

    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv_content = (TextView) findViewById(R.id.tv_content);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        fragmentManager = getFragmentManager();

        // tampilan default awal ketika aplikasii dijalankan
        if (savedInstanceState == null) {
            tv_content.setVisibility(View.GONE);
            fragment = new HomeMenuFragment();
            callFragment(fragment);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            tv_content.setVisibility(View.GONE);
            fragment = new HomeMenuFragment();
        } else if (id == R.id.nav_profile) {
            tv_content.setVisibility(View.GONE);
            fragment = new ProfileMenuFragment();
        } else if (id == R.id.nav_open_live_account) {
            tv_content.setVisibility(View.GONE);
            fragment = new OpenLiveAccountMenuFragment();
        } else if (id == R.id.nav_change_password) {
            tv_content.setVisibility(View.GONE);
            fragment = new ChangePasswordMenuFragment();
        } else if (id == R.id.nav_signout) {
            tv_content.setVisibility(View.VISIBLE);
            tv_content.setText("Sign Out");
        }

        callFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // untuk mengganti isi kontainer menu yang dipiih
    private void callFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.remove(fragment);
        fragmentTransaction.replace(R.id.frame_container_navigation, fragment);
        fragmentTransaction.commit();
    }
}
