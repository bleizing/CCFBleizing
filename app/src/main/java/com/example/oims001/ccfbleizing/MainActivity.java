package com.example.oims001.ccfbleizing;

import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
        ProductFragement productFragement = new ProductFragement();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, productFragement, "ProductFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}