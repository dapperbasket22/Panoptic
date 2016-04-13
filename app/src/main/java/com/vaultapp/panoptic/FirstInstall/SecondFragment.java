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
public class SecondFragment extends Fragment implements View.OnClickListener {


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        Button over = (Button) view.findViewById(R.id.screenButton);
        Button nxtlock = (Button) view.findViewById(R.id.nxtlock);

        over.setOnClickListener(this);
        nxtlock.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.screenButton :
                // Get to the security settings
                startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
                break;
            case R.id.nxtlock :
                final EnableNotFragment enableNotFragment = new EnableNotFragment();
                final FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //Enable notification fragment
                transaction.replace(R.id.fragmentContainer, enableNotFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
