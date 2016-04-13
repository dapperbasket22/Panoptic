package com.vaultapp.panoptic.FirstInstall;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vaultapp.panoptic.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnableNotFragment extends Fragment implements View.OnClickListener {


    public EnableNotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_enable_not, container, false);

        Button but99 = (Button) view.findViewById(R.id.button99);
        Button notYet = (Button) view.findViewById(R.id.notYet);

        but99.setOnClickListener(this);
        notYet.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button99:
                // Enabling app for receiving notification
                startActivityForResult(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS),0);
                break;
            case R.id.notYet:
                final SetPassFragment setPassFragment = new SetPassFragment();
                final FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //Fragment to set vault passwords
                transaction.replace(R.id.fragmentContainer, setPassFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
