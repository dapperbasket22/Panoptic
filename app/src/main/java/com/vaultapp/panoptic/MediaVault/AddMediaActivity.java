package com.vaultapp.panoptic.MediaVault;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.vaultapp.panoptic.R;


public class AddMediaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AbsListView.MultiChoiceModeListener{

    GridView gridView;
    Cursor cursor;
    int flag;
    MediaAdapter mediaAdapter,adapter; //Album Adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_media);

        gridView = (GridView) findViewById(R.id.addMedia);
        flag = 0; // Folder View

        //Query Table
        String[] projection = {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA};
        String groupBy =  "2) GROUP BY 2,(3";   // To get folder only
        cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, groupBy, null, null); //Get content
        mediaAdapter = new MediaAdapter(this,cursor,0);
        //GridView Options
        gridView.setAdapter(mediaAdapter);
        gridView.setOnItemClickListener(this);
        gridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        gridView.setMultiChoiceModeListener(this);
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
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        flag = 1;
        MediaData temp = (MediaData) parent.getItemAtPosition(position);
        if(temp.flag == 0){     // Move inside Folder
            String bucket_name = temp.name;
            String searchParam = "bucket_display_name = \""+bucket_name+"\"";
            String[] projection = {MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.Media.DATA };
            cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,searchParam,null,null);
            adapter = new MediaAdapter(this,cursor,1);
            gridView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (flag==1){
            flag = 0;
            gridView.setAdapter(mediaAdapter);
        } else {
            finish();
        }
    }
}
