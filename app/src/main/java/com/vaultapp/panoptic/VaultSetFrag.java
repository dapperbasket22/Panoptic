package com.vaultapp.panoptic;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class VaultSetFrag extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_vault);
    }
}
