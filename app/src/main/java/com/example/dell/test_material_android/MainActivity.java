package com.example.dell.test_material_android;


import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private boolean fabsShown;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab);
        MaterialButton mb = findViewById(R.id.mb);
        View sheet = findViewById(R.id.sheet);
        View scrim = findViewById(R.id.scrim);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setExpanded(!fab.isExpanded());
            }
        });
        sheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setExpanded(false);
            }
        });
        scrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setExpanded(false);
            }
        });
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabsShown) {
                    fab.show();
                } else {
                    fab.hide();
                }
                fabsShown = !fabsShown;
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (fab.isExpanded()) {
            fab.setExpanded(false);
        }
        super.onBackPressed();
    }
}
