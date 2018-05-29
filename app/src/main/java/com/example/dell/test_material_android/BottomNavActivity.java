package com.example.dell.test_material_android;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;

import com.example.dell.test_material_android.fragment.BlankFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class BottomNavActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };
    private FragmentTransaction fragmentTransaction;
    private BlankFragment blankFragment1, blankFragment2, blankFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        blankFragment1 = BlankFragment.newInstance("1", "1");
        blankFragment2 = BlankFragment.newInstance("2", "2");
        blankFragment3 = BlankFragment.newInstance("3", "4");

        fragmentTransaction.commit();
    }

}
