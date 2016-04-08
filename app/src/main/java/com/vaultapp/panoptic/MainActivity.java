package com.vaultapp.panoptic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.vaultapp.panoptic.LockScreen.LockService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements AbsListView.MultiChoiceModeListener {
    SharedPreferences vaultPref;
    ListView vaultFiles;
    FileListAdapter adapterVault;
    SharedPreferences.Editor vaultEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = new Intent(this,AddFile.class);
        vaultFiles = (ListView) findViewById(R.id.vault_files);
        vaultPref = PreferenceManager.getDefaultSharedPreferences(this);
        vaultEdit = vaultPref.edit();

        startService(new Intent(this, LockService.class));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        vaultFiles.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        vaultFiles.setMultiChoiceModeListener(this);
    }

    void fill(){
        Set<String> temp = vaultPref.getStringSet("vault_private",new HashSet<String>());
        ArrayList<String> data = new ArrayList<>();
        for(String s : temp){
            data.add(s);
        }
        adapterVault = new FileListAdapter(this,data);
        vaultFiles.setAdapter(adapterVault);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onResume() {
        fill();
        super.onResume();
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
                SparseBooleanArray selected = vaultFiles.getCheckedItemPositions();
                for(int i=(selected.size()-1); i>=0;i--){
                    if(selected.valueAt(i)){
                        DisplayData d = (DisplayData) adapterVault.getItem(selected.keyAt(i));
                        adapterVault.remove(d);
                    }
                }
                ArrayList<DisplayData> temp = adapterVault.getList();
                Set<String> putData = new HashSet<>();
                for(DisplayData d : temp){
                    File f = new File(d.source,d.name);
                    putData.add(f.toString());
                }
                vaultEdit.putStringSet("vault_private",putData);
                vaultEdit.commit();
                Toast.makeText(this, "Removed from vault", Toast.LENGTH_SHORT).show();
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
