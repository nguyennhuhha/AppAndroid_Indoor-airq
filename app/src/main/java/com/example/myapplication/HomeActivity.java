package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class HomeActivity extends AppCompatActivity {
    private FragmentManager fm;
    public AnimatedBottomBar navbar;

    public HomeFragment homeFrag;
    public MapFragment mapFrag;
    public ChartFragment chartFrag;
    public AccountFragment accFrag;
    private Fragment fragment;

    int selectedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                navbar.selectTabAt(0, true);
//            }
//        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        InitVars();
        InitViews();
        InitEvents();

        fm.beginTransaction().add(R.id.main_frame, mapFrag, "map").commit();
        fm.beginTransaction().hide(mapFrag).commit();

        fm.beginTransaction().add(R.id.main_frame, chartFrag, "chart").commit();
        fm.beginTransaction().hide(chartFrag).commit();

        fm.beginTransaction().add(R.id.main_frame, accFrag, "acc").commit();
        fm.beginTransaction().hide(accFrag).commit();

        fm.beginTransaction().add(R.id.main_frame, homeFrag, "home").commit();
        fragment = homeFrag;
        navbar.selectTabAt(0, false);
    }

    private void InitVars() {
        selectedIndex = 0;

        fm = getSupportFragmentManager();

        homeFrag = new HomeFragment();
        mapFrag = new MapFragment();
        chartFrag= new ChartFragment();
        accFrag = new AccountFragment();
    }
    private void InitViews() {
        navbar = findViewById(R.id.bottom_bar);
    }

    private void InitEvents() {
        navbar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int lastIndex, @Nullable AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {

                switch (newIndex) {
                    case 0:
                        fm.beginTransaction().hide(fragment).commit();
                        fragment = homeFrag;
                        break;
                    case 1:
                        fm.beginTransaction().hide(fragment).commit();
                        fragment = mapFrag;
                        break;
                    case 2:
                        fm.beginTransaction().hide(fragment).commit();
                        fragment = chartFrag;
                        break;
                    case 3:
                        fm.beginTransaction().hide(fragment).commit();
                        fragment = accFrag;
                        break;
                }
                fm.beginTransaction()
                        .show(fragment)
                        .commit();
            }

            @Override
            public void onTabReselected(int i, @NonNull AnimatedBottomBar.Tab tab) {

            }
        });
    }

}