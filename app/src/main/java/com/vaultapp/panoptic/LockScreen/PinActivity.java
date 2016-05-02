package com.vaultapp.panoptic.LockScreen;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vaultapp.panoptic.MediaVault.SplashStart;
import com.vaultapp.panoptic.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PinActivity extends AppCompatActivity implements View.OnClickListener {
    int count = 0;
    String pinPu, pinPr, enter_pin="";
    EditText pinText;
    TextView wrongText;
    HomeKeyLocker homeKeyLocker;
    SharedPreferences pinPref;
    SharedPreferences.Editor pinEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        pinPref = PreferenceManager.getDefaultSharedPreferences(this);
        pinEdit = pinPref.edit();
        homeKeyLocker = new HomeKeyLocker();
        pinText = (EditText) findViewById(R.id.pin_container);
        wrongText = (TextView) findViewById(R.id.wrong_pin);

        //Buttons
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button clear_button = (Button) findViewById(R.id.clear_button);
        Button back_button = (Button) findViewById(R.id.back_screen);

        //Set onclick listener
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        clear_button.setOnClickListener(this);
        back_button.setOnClickListener(this);

        homeKeyLocker.lock(this);
    }

    @Override
    public void onClick(View v) {
        //Press the keys to enter password
        switch (v.getId()){
            case R.id.back_screen :
                enter_pin = "";
                pinText.setText(enter_pin);
                count = 0;
                wrongText.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(this,LockScreenActivity.class);
                startActivity(intent);
                break;
            case R.id.clear_button :
                count = 0;
                enter_pin = "";
                pinText.setText(enter_pin);
                break;
            case R.id.button0 :
                count++;
                enter_pin += "0";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button1 :
                count++;
                enter_pin += "1";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button2 :
                count++;
                enter_pin += "2";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button3 :
                count++;
                enter_pin += "3";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button4 :
                count++;
                enter_pin += "4";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button5 :
                count++;
                enter_pin += "5";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button6 :
                count++;
                enter_pin += "6";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button7 :
                count++;
                enter_pin += "7";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button8 :
                count++;
                enter_pin += "8";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            case R.id.button9 :
                count++;
                enter_pin += "9";
                pinText.setText(enter_pin);
                if (count==4){
                    wall();
                }
                break;
            default:
        }
    }

    void wall(){
        count=0;
        pinPu = pinPref.getString("pub_pass","1234");
        pinPr = pinPref.getString("pri_pass","4321");
        if(enter_pin.equals(pinPr)){
            Boolean flag = true;
            Set<String> getData = new HashSet<>();
            Set<String> putData = new HashSet<>();
            getData = pinPref.getStringSet("vault_private", new HashSet<String>());
            ArrayList<String> temp = new ArrayList<>();
            for(String s : getData){
                File f = new File(s);
                if(f.isHidden()){
                    File x = new File(f.getParent(),f.getName().substring(1));
                    f.renameTo(x);
                    temp.add(x.toString());
                }else{
                    flag = false;
                    break;
                }
            }
            if(flag){
                for (String s : temp){
                    putData.add(s);
                }
                pinEdit.putStringSet("vault_private",putData);
                pinEdit.commit();
                PackageManager p = getPackageManager();
                ComponentName componentName = new ComponentName(this, SplashStart.class);
                p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            }
            wrongText.setVisibility(View.INVISIBLE);
            toHome();
        } else if(enter_pin.equals(pinPu)) {
            Boolean flag = true;
            Set<String> getData = new HashSet<>();
            Set<String> putData = new HashSet<>();
            getData = pinPref.getStringSet("vault_private", new HashSet<String>());
            ArrayList<String> temp = new ArrayList<>();
            for(String s : getData){
                File f = new File(s);
                if(!f.isHidden()){
                    File x = new File(f.getParent(),"."+f.getName());
                    f.renameTo(x);
                    temp.add(x.toString());
                }else{
                    flag = false;
                    break;
                }
            }
            if(flag){
                for (String s : temp){
                    putData.add(s);
                }
                pinEdit.putStringSet("vault_private",putData);
                pinEdit.commit();
                PackageManager p = getPackageManager();
                ComponentName componentName = new ComponentName(this, SplashStart.class); // activity which is first time open in manifiest file which is declare as <category android:name="android.intent.category.LAUNCHER" />
                p.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            }
            wrongText.setVisibility(View.INVISIBLE);
            toHome();
        } else {
            enter_pin = "";
            pinText.setText(enter_pin);
            wrongText.setVisibility(View.VISIBLE);
        }
    }
    void toHome(){
        homeKeyLocker.unlock();
        finish();
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    protected void onDestroy() {
        homeKeyLocker = null;
        super.onDestroy();
    }
}