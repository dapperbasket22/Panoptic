package com.vaultapp.panoptic.MediaVault;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.File;

public class DisplayData {
    public String name, source;
    boolean dir = false;
    Bitmap icon;

    DisplayData(File F){
        name = F.getName();
        source = F.getParent();
        icon = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(F.toString()),120,120);
        if(F.isDirectory())
            dir = true;
    }
    public String toString(){
        return source+"/"+name;
    }
}
