package com.example.asus.gp1;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.asus.gp1.Helper.MetaData;

public class MainActivity extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener,
        ClassFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        TeacherClassFragment.OnFragmentInteractionListener,
        stdinfoFragment.OnFragmentInteractionListener {

    HomeFragment f1;
    ClassFragment f2;
    InfoFragment f3;
    TeacherClassFragment f4;
    stdinfoFragment f5;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle("校园助手");
                    initf1();
                    return true;
                case R.id.navigation_dashboard:
                    if (MetaData.Role.equals("Teacher")) {
                        setTitle("您开设的课程");
                        initf4();
                    } else {
                        setTitle("您参加的课程");
                        initf2();
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (MetaData.Role.equals("Teacher")) {
                        setTitle("签到情况");
                        initf3();
                    } else {
                        setTitle("签到系统");
                        initf5();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initf1();

    }

    private void hideFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);
        }
        if (f2 != null) {
            transaction.hide(f2);
        }
        if (f3 != null) {
            transaction.hide(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
        }
        if (f5 != null) {
            transaction.hide(f5);
        }
    }

    private void initf1() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (f1 == null) {
            f1 = new HomeFragment();
            transaction.add(R.id.FrameLayoutMain, f1);
        }
        hideFragment(transaction);
        transaction.show(f1);
        transaction.commit();
    }

    private void initf2() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (f2 == null) {
            f2 = new ClassFragment();
            transaction.add(R.id.FrameLayoutMain, f2);
        }
        hideFragment(transaction);
        transaction.show(f2);
        transaction.commit();
    }

    private void initf3() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (f3 == null) {
            f3 = new InfoFragment();
            transaction.add(R.id.FrameLayoutMain, f3);
        }
        hideFragment(transaction);
        transaction.show(f3);
        transaction.commit();
    }

    private void initf4() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (f4 == null) {
            f4 = new TeacherClassFragment();
            transaction.add(R.id.FrameLayoutMain, f4);
        }
        hideFragment(transaction);
        transaction.show(f4);
        transaction.commit();
    }

    private void initf5() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (f5 == null) {
            f5 = new stdinfoFragment();
            transaction.add(R.id.FrameLayoutMain, f5);
        }
        hideFragment(transaction);
        transaction.show(f5);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
