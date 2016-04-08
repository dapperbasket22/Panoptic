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
public class SecondFragment extends Fragment {

    static final int LOCK_REQUEST = 0;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        Button over = (Button) view.findViewById(R.id.screenButton);
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivityForResult(new Intent(Settings.ACTION_SECURITY_SETTINGS),LOCK_REQUEST);
            }
        });
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOCK_REQUEST){
            if(LockType.getCurrent(getContext().getContentResolver()) == 1){
                final EnableNotFragment enableNotFragment = new EnableNotFragment();
                final FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, enableNotFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }
}
