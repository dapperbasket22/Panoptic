package com.vaultapp.panoptic.MediaVault;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaultapp.panoptic.R;

import java.util.ArrayList;

public class MediaAdapter extends BaseAdapter {
    ArrayList<MediaData> data;
    Context context;

    MediaAdapter(Context context, Cursor cursor,int flag){
        this.context = context;
        data = new ArrayList<>();
        data = getMedia(cursor,flag);
    }

    ArrayList<MediaData> getMedia(Cursor cursor,int flag){
        ArrayList<MediaData> tob = new ArrayList<>();
        if(cursor!=null){
            cursor.moveToFirst();
            int t1 = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            int t2;
            if(flag==0){
                t2 = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            } else {
                t2 = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
            }
            do{
                tob.add(new MediaData(cursor, t1, t2, flag));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return tob;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MyViewHolder holder = null;

        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.list_item_grid,parent,false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        MediaData temp = data.get(position);
        holder.icon.setImageBitmap(temp.icon);
        holder.name.setText(temp.name);
        return row;
    }

    class MyViewHolder {
        ImageView icon;
        TextView name;
        MyViewHolder(View view){
            icon = (ImageView) view.findViewById(R.id.media_icon);
            name = (TextView) view.findViewById(R.id.media_name);
        }
    }
}
