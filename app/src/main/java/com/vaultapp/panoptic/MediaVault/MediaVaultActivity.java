package com.vaultapp.panoptic.MediaVault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.vaultapp.panoptic.LockScreen.LockService;
import com.vaultapp.panoptic.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MediaVaultActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static SharedPreferences vaultPref;
    static SharedPreferences.Editor vaultEdit;
    static Set<String> temp;
    static ArrayList<String> data,image_ar,video_ar;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_vault);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        startService(new Intent(this, LockService.class)); //Lock Screen

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        vaultPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        vaultEdit = vaultPref.edit();
        updateData();

        final Intent intent = new Intent(this,AddFile.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(intent));
            }
        });

    }

    static void updateData(){
        temp = vaultPref.getStringSet("vault_private",new HashSet<String>());
        data = new ArrayList<>();
        image_ar = new ArrayList<>();
        video_ar = new ArrayList<>();

        for(String s : temp){
            data.add(s);
            if(s.endsWith(".png") || s.endsWith(".jpg") || s.endsWith(".jpeg")){
                image_ar.add(s);
            }else if (s.endsWith(".mkv") || s.endsWith(".flv") || s.endsWith(".avi") || s.endsWith(".mp4")){
                video_ar.add(s);
            }
        }
    }

    @Override
    protected void onResume() {
        updateData();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media_vault, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,VaultSetting.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment implements AbsListView.MultiChoiceModeListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        FileListAdapter adapterVault;
        GridView imgList;
        TextView nullText;
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_image_vault, container, false);

            nullText = (TextView) rootView.findViewById(R.id.nullText);
            nullText.setVisibility(View.VISIBLE);

            imgList = (GridView) rootView.findViewById(R.id.imageGridView);
            imgList.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
            imgList.setMultiChoiceModeListener(this);

            fill();
            return rootView;
        }

        @Override
        public void onResume() {
            fill();
            super.onResume();
        }
        void fill(){
            switch(getArguments().getInt(ARG_SECTION_NUMBER)){
                case 0 :
                    adapterVault = new FileListAdapter(getContext(),image_ar,nullText);
                    break;
                case 1 :
                    adapterVault = new FileListAdapter(getContext(),video_ar,nullText);
                    break;
                case 2 :
                    adapterVault = new FileListAdapter(getContext(),data,nullText);
                    break;
                default : break;
            }
            imgList.setAdapter(adapterVault);
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.view_file_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    // Get selected items
                    SparseBooleanArray selected = imgList.getCheckedItemPositions();
                    for(int i=(selected.size()-1); i>=0;i--){
                        if(selected.valueAt(i)){
                            DisplayData d = (DisplayData) adapterVault.getItem(selected.keyAt(i));
                            adapterVault.remove(d);
                            data.remove(d.source+"/"+d.name);
                            // Remove file from vault
                        }
                    }
                    // Update list of hidden files
                    Set<String> putData = new HashSet<>();
                    for(String d : data){
                        putData.add(d);
                    }
                    vaultEdit.putStringSet("vault_private",putData);
                    vaultEdit.commit();
                    updateData();
                    Toast.makeText(getContext(), "Removed from vault", Toast.LENGTH_SHORT).show();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Images";
                case 1:
                    return "Videos";
                case 2:
                    return "All Files";
            }
            return null;
        }
    }

}
