package com.eneko.hexcolortimewallpaper;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

public class Preferences extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        Preference applyWallpaper = findPreference("applyWallpaper");
        applyWallpaper.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);

                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(getActivity(), Wallpaper.class));

                startActivity(intent);

                return true;
            }
        });
    }

}
