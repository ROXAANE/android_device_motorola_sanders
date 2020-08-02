package com.moto.actions.spectrum;

import android.app.Activity;
import android.content.res.Resources;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.widget.AdapterView.OnItemClickListener;

import com.moto.actions.R;

public class SpectrumProfilesActivity extends PreferenceActivity
             implements Preference.OnPreferenceChangeListener {

   private static final String SPECTRUM_KEY = "spectrum";
   private static final String SPECTRUM_CATEGORY = "spectrum_category";
   private static final String SPECTRUM_SYSTEM_PROPERTY = "persist.spectrum.profile";

   private ListPreference mSpectrum;
   private Preference mSpectrumCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.spectrum_profiles);

        final PreferenceScreen prefScreen = getPreferenceScreen();

        mSpectrum = (ListPreference) findPreference(SPECTRUM_KEY);
        if( mSpectrum != null ) {
            mSpectrum.setValue(SystemProperties.get(SPECTRUM_SYSTEM_PROPERTY, "0"));
            mSpectrum.setOnPreferenceChangeListener(this);
        }

        mSpectrumCategory = (Preference) findPreference(SPECTRUM_CATEGORY);
        if (mSpectrumCategory != null
                && !getResources().getBoolean(R.bool.config_hasSpectrumSupport)) {
           prefScreen.removePreference(mSpectrumCategory);
        }
    }

     @Override
     public boolean onPreferenceChange(Preference preference, Object newValue) {
         final String key = preference.getKey();
         boolean value;
         String strvalue;
         if (SPECTRUM_KEY.equals(key)) {
            strvalue = (String) newValue;
            SystemProperties.set(SPECTRUM_SYSTEM_PROPERTY, strvalue);
            return true;
         }
        return true;
     }
}


