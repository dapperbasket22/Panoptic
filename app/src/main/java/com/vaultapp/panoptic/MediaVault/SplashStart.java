package com.vaultapp.panoptic.MediaVault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vaultapp.panoptic.FirstInstall.FirstActivity;
import com.vaultapp.panoptic.R;

public class SplashStart extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_start);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean val = pref.getBoolean("first_install",true);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Starts activity
                if(val){
                    startActivity(new Intent(SplashStart.this, FirstActivity.class));
                } else {
                    startActivity(new Intent(SplashStart.this,MediaVaultActivity.class));
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
