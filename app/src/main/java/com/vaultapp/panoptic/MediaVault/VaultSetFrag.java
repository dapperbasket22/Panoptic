package com.vaultapp.panoptic.MediaVault;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.vaultapp.panoptic.R;

public class VaultSetFrag extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_vault);
    }
}
