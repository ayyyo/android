package com.ayyayo.g.ui;

import com.ayyayo.g.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;

import android.preference.Preference.OnPreferenceClickListener;

public class PreferenceActivity extends android.preference.PreferenceActivity {
    Preference cat;
    ProgressDialog mProgressDialog;
    SharedPreferences sp;
    Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        sp = getSharedPreferences("GCM", MODE_PRIVATE);
        edit = sp.edit();
        mProgressDialog = new ProgressDialog(PreferenceActivity.this);
        mProgressDialog.setMessage("Loading...");

        mProgressDialog.setCancelable(false);

        cat = (Preference) findPreference("contact");

        cat.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(PreferenceActivity.this,
                        CustomeWebView.class);
                intent.putExtra("link", "https://goo.gl/forms/oOGce8OXYZGhdfu43");

                startActivity(intent);
                return false;
            }
        });

        cat = (Preference) findPreference("about");

        cat.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(PreferenceActivity.this,
                        CustomeWebView.class);
                intent.putExtra("link", "https://goo.gl/forms/oOGce8OXYZGhdfu43");

                startActivity(intent);
                return false;
            }
        });

    }
}
