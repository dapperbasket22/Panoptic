package com.vaultapp.panoptic.MediaVault;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vaultapp.panoptic.R;

import java.io.File;
import java.util.ArrayList;

public class FileListAdapter extends BaseAdapter {
    ArrayList<DisplayData> fileData;
    Context context;

    FileListAdapter(Context context, File base){
        fileData = new ArrayList<>();
        this.context = context;
        for (File F : base.listFiles()){
            if(!F.isHidden()){
                fileData.add(new DisplayData(F));
            }
        }
    }


    public FileListAdapter(Context context, ArrayList<String> list,TextView txt){
        fileData = new ArrayList<>();
        this.context = context;
        for(String s : list){
            File f = new File(s);
            fileData.add(new DisplayData(f));
        }
        if(list.size()==0)
            txt.setVisibility(View.VISIBLE);
        else
            txt.setVisibility(View.GONE);
    }

    @Override
    public int getCount() {
        return fileData.size();
    }

    @Override
    public Object getItem(int position) {
        return fileData.get(position);
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
        DisplayData temp = fileData.get(position);
        holder.file_name.setText(temp.name);
        if(temp.dir){
            holder.icon.setImageResource(R.drawable.ic_folder);
        }else {
            if(temp.icon == null){
                holder.icon.setImageResource(R.drawable.ic_file);
            }else{
                holder.icon.setImageBitmap(temp.icon);
            }
        }
        return row;
    }

    class MyViewHolder {
        TextView file_name;
        ImageView icon;
        MyViewHolder(View view){
            file_name = (TextView) view.findViewById(R.id.media_name);
            icon = (ImageView) view.findViewById(R.id.media_icon);
        }
    }

    public void remove(DisplayData data){
        fileData.remove(data);
        File f = new File(data.source,data.name);
        if (f.isHidden()){
            File x = new File(data.source,data.name.substring(1));
            f.renameTo(x);
        }
        notifyDataSetChanged();
    }

    ArrayList<DisplayData> getList(){
        return fileData;
    }

}
