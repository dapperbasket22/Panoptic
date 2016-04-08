package com.vaultapp.panoptic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    FileListAdapter(Context context,ArrayList<String> list){
        fileData = new ArrayList<>();
        this.context = context;
        for(String s : list){
            File f = new File(s);
            fileData.add(new DisplayData(f));
        }
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
            row = layoutInflater.inflate(R.layout.list_item,parent,false);
            holder = new MyViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (MyViewHolder) row.getTag();
        }
        DisplayData temp = fileData.get(position);
        holder.file_name.setText(temp.name);
        holder.file_source.setText(temp.source);
        return row;
    }

    class MyViewHolder {
        TextView file_name,file_source;
        MyViewHolder(View view){
            file_name = (TextView) view.findViewById(R.id.item_name);
            file_source = (TextView) view.findViewById(R.id.item_subtext);
        }
    }

    void remove(DisplayData data){
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
