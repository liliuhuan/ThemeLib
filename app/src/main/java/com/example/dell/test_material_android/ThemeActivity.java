package com.example.dell.test_material_android;


import android.os.Bundle;
import android.view.View;
import com.example.dell.test_material_android.theme.ThemeOverlayUtils;
import com.example.dell.test_material_android.theme.ThemeSwitcherDialogFragment;

import androidx.appcompat.app.AppCompatActivity;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeOverlayUtils.applyThemeOverlays(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        findViewById(R.id.mbutton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ThemeSwitcherDialogFragment themeSwitcherDialogFragment = null;
        if (themeSwitcherDialogFragment == null){
            themeSwitcherDialogFragment = new ThemeSwitcherDialogFragment();
        }
        themeSwitcherDialogFragment.show(getSupportFragmentManager(), "theme-switcher");
    }
}
