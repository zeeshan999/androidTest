package com.androidtest.ui.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.androidtest.ui.Fragment.MoviesFragment;
import com.androidtest.R;

public class MainActivity extends FragmentActivity {

    MoviesFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new MoviesFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer,fragment, "moviesFragment").commit();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        fragment = null;


    }
}
