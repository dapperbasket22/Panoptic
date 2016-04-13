package com.vaultapp.panoptic.FirstInstall;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vaultapp.panoptic.MainActivity;
import com.vaultapp.panoptic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetPassFragment extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    public SetPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_pass, container, false);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = pref.edit();

        final EditText pubPin = (EditText) view.findViewById(R.id.pub_pin_container);
        final EditText priPin = (EditText) view.findViewById(R.id.pri_pin_container);

        final TextView priText = (TextView) view.findViewById(R.id.priText1);

        Button allSet = (Button) view.findViewById(R.id.allset);
        allSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boom(pubPin,priPin,priText)) {
                    edit.putBoolean("first_install", false);
                    edit.commit();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    //Main activity of the app
                    getActivity().finish();
                }
            }
        });

        return view;
    }

    Boolean boom(EditText a, EditText b,TextView c){
        String f1 = a.getText().toString();
        String f2 = b.getText().toString();
        if(f1.length() == 4 && f2.length() == 4){
            edit.putString("pub_pass",f1);
            edit.putString("pri_pass",f2);
            edit.commit();
            return true;
        }else{
            c.setVisibility(View.VISIBLE);
            return false;
        }
    }
}
