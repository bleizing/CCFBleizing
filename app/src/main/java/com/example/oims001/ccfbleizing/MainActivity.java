package com.example.oims001.ccfbleizing;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().addOnBackStackChangedListener(this);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mainFragment, "MainFragment").commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int option = savedInstanceState.getInt("ActionBar");

        final boolean isShowHomeEnabled = (option & ActionBar.DISPLAY_SHOW_HOME) != 0;
        final boolean isHomeAsUpEnabled = (option & ActionBar.DISPLAY_HOME_AS_UP) != 0;
        final boolean isShowTitleEnabled = (option & ActionBar.DISPLAY_SHOW_TITLE) != 0;
        final boolean isUseLogoEnabled = (option & ActionBar.DISPLAY_USE_LOGO) != 0;
        final boolean isShowCustomEnabled = (option & ActionBar.DISPLAY_SHOW_CUSTOM) != 0;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(isShowHomeEnabled);
        actionBar.setDisplayHomeAsUpEnabled(isHomeAsUpEnabled);
        actionBar.setDisplayShowTitleEnabled(isShowTitleEnabled);
        actionBar.setDisplayUseLogoEnabled(isUseLogoEnabled);
        actionBar.setDisplayShowCustomEnabled(isShowCustomEnabled);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
        outState.putInt("ActionBar", getSupportActionBar().getDisplayOptions());
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    public void shouldDisplayHomeUp () {
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    public void changeToProductFragment() {
        ProductFragement fragement = (ProductFragement) getSupportFragmentManager().findFragmentByTag("ProductFragment");

        if (fragement == null || !fragement.isInLayout()) {
            fragement = new ProductFragement();
        }

        if (!fragement.isVisible()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragement, "ProductFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void changeToWebviewFragment() {
        WebviewFragment fragement = (WebviewFragment) getSupportFragmentManager().findFragmentByTag("WebviewFragment");

        if (fragement == null || !fragement.isInLayout()) {
            fragement = new WebviewFragment();
        }

        if (!fragement.isVisible()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragement, "WebviewFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void changeToMapsFragment() {
        MapsFragment fragement = (MapsFragment) getSupportFragmentManager().findFragmentByTag("MapsFragment");

        if (fragement == null || !fragement.isInLayout()) {
            fragement = new MapsFragment();
        }

        if (!fragement.isVisible()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragement, "MapsFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void changeToKategoriFragment() {
        KategoriFragment fragment = (KategoriFragment) getSupportFragmentManager().findFragmentByTag("KategoriFragment");

        if (fragment == null || !fragment.isInLayout()) {
            fragment = new KategoriFragment();
        }

        if (!fragment.isVisible()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "KategoriFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void openMapsActivity(double lat, double lng, String address) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("Lat", lat);
        bundle.putDouble("Lng", lng);
        bundle.putString("Address", address);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
