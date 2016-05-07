package com.vaultapp.panoptic.FirstInstall;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

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

        final SecondFragment secondFragment = new SecondFragment();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        final ImageSwitcher imgswitch = (ImageSwitcher) view.findViewById(R.id.imageSwitcher);
        imgswitch.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView myView = new ImageView(getContext());
                myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                myView.setLayoutParams(new ImageSwitcher.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
                return myView;
            }
        });

        final int[] imgRes = {R.drawable.screen1, R.drawable.screen2, R.drawable.screen3,
            R.drawable.screen4, R.drawable.screen5, R.drawable.screen6};

        imgswitch.setImageResource(imgRes[0]);

        imgswitch.postDelayed(new Runnable() {
            int i = 1;
            public void run() {
                imgswitch.setImageResource(imgRes[i%5]);
                imgswitch.postDelayed(this, 2000);
                i++;
            }
        }, 3000);



        Animation in = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(),android.R.anim.slide_out_right);
        imgswitch.setInAnimation(in);
        imgswitch.setOutAnimation(out);

        //Button next = (Button) view.findViewById(R.id.enableButton);
        FloatingActionButton next = (FloatingActionButton) view.findViewById(R.id.enableButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fragment for disabling default lock
                transaction.replace(((ViewGroup)(getView().getParent())).getId(), secondFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

}
