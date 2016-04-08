package com.vaultapp.panoptic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class VaultSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vault_set);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new VaultSetFrag())
                .commit();

    }

}
