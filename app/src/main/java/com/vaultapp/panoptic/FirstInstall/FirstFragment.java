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
public class FirstFragment extends Fragment {

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        final int lockType = LockType.getCurrent(getContext().getContentResolver());
        final SecondFragment secondFragment = new SecondFragment();
        final SetPassFragment setPassFragment = new SetPassFragment();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Button next = (Button) view.findViewById(R.id.enableButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lockType!=1){
                    transaction.replace(R.id.fragmentContainer, secondFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    transaction.replace(R.id.fragmentContainer, setPassFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

}
