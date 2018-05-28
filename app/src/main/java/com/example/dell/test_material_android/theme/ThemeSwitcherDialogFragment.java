/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.dell.test_material_android.theme;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.example.dell.test_material_android.R;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.widget.CompoundButtonCompat;
import androidx.fragment.app.DialogFragment;


/**
 * Theme switcher dialog that allows the user to choose a primary or secondary palette to overlay
 * above the app theme.
 */
public class ThemeSwitcherDialogFragment extends DialogFragment {

    @StyleableRes
    private static final int[] PRIMARY_THEME_OVERLAY_ATTRS = {
            R.attr.colorPrimary,R.attr.colorPrimaryDark
    };

    @StyleableRes
    private static final int[] SECONDARY_THEME_OVERLAY_ATTRS = {R.attr.colorSecondary, R.attr.colorPrimaryDark};

    ThemeSwitcherResourceProvider resourceProvider = new ThemeSwitcherResourceProvider();
    private RadioGroup primaryGroup;
    private RadioGroup secondaryGroup;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.mtrl_theme_switcher_title)
                .setView(onCreateDialogView(getActivity().getLayoutInflater()))
                .setPositiveButton(
                        R.string.mtrl_theme_switcher_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                                applyThemeOverlays();
                            }
                        })
                .setNegativeButton(R.string.mtrl_theme_switcher_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    private View onCreateDialogView(LayoutInflater layoutInflater) {
        View view = layoutInflater.inflate(R.layout.mtrl_theme_switcher_dialog, null);

        int[] currentThemeOverlays = ThemeOverlayUtils.getThemeOverlays();

        primaryGroup = view.findViewById(R.id.primary_colors);
        initializeColors(
                primaryGroup,
                resourceProvider.getPrimaryColors(),
                resourceProvider.getPrimaryColorsContentDescription(),
                PRIMARY_THEME_OVERLAY_ATTRS,
                currentThemeOverlays.length >= 2 ? currentThemeOverlays[0] : 0);

        secondaryGroup = view.findViewById(R.id.secondary_colors);
        initializeColors(
                secondaryGroup,
                resourceProvider.getSecondaryColors(),
                resourceProvider.getSecondaryColorsContentDescription(),
                SECONDARY_THEME_OVERLAY_ATTRS,
                currentThemeOverlays.length >= 2 ? currentThemeOverlays[1] : 0);

        return view;
    }

    private void applyThemeOverlays() {
        ColorPalette primaryPalette =
                (ColorPalette) getDialog().findViewById(primaryGroup.getCheckedRadioButtonId()).getTag();
        ColorPalette secondaryPalette =
                (ColorPalette) getDialog().findViewById(secondaryGroup.getCheckedRadioButtonId()).getTag();

        ThemeOverlayUtils.setThemeOverlays(
                getActivity(), primaryPalette.themeOverlay, secondaryPalette.themeOverlay);
    }

    private void initializeColors(
            RadioGroup group,
            @ArrayRes int colors,
            @ArrayRes int colorContentDescriptions,
            @StyleableRes int[] themeOverlayAttrs,
            @StyleRes int currentThemeOverlay) {
        TypedArray colorsArray = getResources().obtainTypedArray(colors);
        TypedArray contentDescriptionArray = getResources().obtainTypedArray(colorContentDescriptions);
        if (colorsArray.length() != contentDescriptionArray.length()) {
            throw new IllegalArgumentException(
                    "Color array length doesn't match its content description array length.");
        }

        for (int i = 0; i < colorsArray.length(); i++) {
            @StyleRes int paletteThemeOverlay = colorsArray.getResourceId(i, 0);
            ColorPalette palette = new ColorPalette(paletteThemeOverlay, themeOverlayAttrs);

            AppCompatRadioButton button = new AppCompatRadioButton(getContext());
            CompoundButtonCompat.setButtonTintList(
                    button, ColorStateList.valueOf(convertToDisplay(palette.main)));
            button.setTag(palette);
            button.setContentDescription(contentDescriptionArray.getString(i));

            group.addView(button);

            if (palette.themeOverlay == currentThemeOverlay || (i == 0 && currentThemeOverlay == 0)) {
                group.check(button.getId());
            }
        }
        colorsArray.recycle();
    }

    @ColorInt
    private int convertToDisplay(@ColorInt int color) {
        return color == Color.WHITE ? Color.BLACK : color;
    }

    private class ColorPalette {
        @StyleRes
        private final int themeOverlay;

        @ColorInt
        private final int main;
        @ColorInt
        private final int dark;

        @SuppressLint("ResourceType")
        public ColorPalette(@StyleRes int themeOverlay, @StyleableRes int[] themeOverlayAttrs) {
            this.themeOverlay = themeOverlay;

            TypedArray a = getContext().obtainStyledAttributes(themeOverlay, themeOverlayAttrs);
            main = a.getColor(0, Color.TRANSPARENT);
            dark = a.getColor(1, Color.TRANSPARENT);
            a.recycle();
        }
    }
}
