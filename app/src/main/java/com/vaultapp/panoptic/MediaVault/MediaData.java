package com.vaultapp.panoptic.MediaVault;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.File;

public class MediaData {
    Bitmap icon;
    String name;
    File data;
    int flag;
    MediaData(Cursor cursor,int t1,int t2, int flag){
        this.flag = flag;
        icon = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(cursor.getString(t1)), 120, 120);
        if(flag==0){
            File temp = new File(cursor.getString(t1));
            data = new File(temp.getParent());
        } else {
            data = new File(cursor.getString(t1));
        }
        name = cursor.getString(t2);
    }
}
