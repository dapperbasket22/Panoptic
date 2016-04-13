package com.vaultapp.panoptic;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class AddFile extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AbsListView.MultiChoiceModeListener {
    ListView addFile;
    FileListAdapter adapterAdd;
    File base;
    SharedPreferences addPref;
    SharedPreferences.Editor addEdit;
    Set<String> addData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);
        //Initialize
        addFile = (ListView) findViewById(R.id.add_file);
        base = Environment.getExternalStorageDirectory();
        addPref = PreferenceManager.getDefaultSharedPreferences(this);
        addEdit = addPref.edit();

        fillList(base);
        addFile.setOnItemClickListener(this);
        addFile.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        addFile.setMultiChoiceModeListener(this);

    }

    void fillList(File file){
        adapterAdd = new FileListAdapter(this,file);
        addFile.setAdapter(adapterAdd);
    }

    @Override
    public void onBackPressed() {
        if (base.toString().equals(Environment.getExternalStorageDirectory().toString())){
            finish();
        } else {
            base = new File(base.getParent());
            fillList(base);
        }
    }


    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.add_file_menu, menu);
        return true;

    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                //Get selected items
                SparseBooleanArray selected = addFile.getCheckedItemPositions();
                //Get previously hidden files
                Set<String> temp = addPref.getStringSet("vault_private", new HashSet<String>());
                Boolean flag = true;
                addData = new HashSet<>();
                for (String s : temp){
                    File f = new File(s);
                    if (!f.isHidden()){
                        flag=false;
                    }
                    addData.add(s);
                }
                // Update hidden files
                for(int i=0; i<selected.size();i++){
                    if(selected.valueAt(i)){
                        DisplayData d = (DisplayData) adapterAdd.getItem(selected.keyAt(i));
                        File f = new File(d.source,d.name);
                        if(flag){
                            File x = new File(d.source,"."+d.name);
                            f.renameTo(x);
                            addData.add(x.toString());
                        } else {
                            addData.add(f.toString());
                        }
                    }
                }
                addEdit.putStringSet("vault_private", addData);
                addEdit.commit();
                Toast.makeText(this,"Added to vault",Toast.LENGTH_SHORT).show();
                mode.finish(); // Action picked, so close the CAB
                return true;
            default:
                return false;
        }

    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DisplayData data = (DisplayData) parent.getItemAtPosition(position);
        base = new File(data.source,data.name);
        if (base.isDirectory()){
            fillList(base);
        } else{
            Toast.makeText(this,"File", Toast.LENGTH_SHORT).show();
        }

    }
}
