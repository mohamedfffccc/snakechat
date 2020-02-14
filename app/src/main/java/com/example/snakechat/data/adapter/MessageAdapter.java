package com.example.snakechat.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.snakechat.R;
import com.example.snakechat.data.adapter.FriendItem;
import com.example.snakechat.view.activity.BaseActivity;

import java.util.ArrayList;

import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;

public class MessageAdapter extends BaseAdapter {
    ArrayList<FriendItem> data;
    Context con;
    BaseActivity activity;

    public MessageAdapter(ArrayList<FriendItem> data, Context con , BaseActivity activity) {
        this.activity=activity;
        this.data = data;
        this.con = con;
        setSharedPreferences(activity);

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
        if (data.get(position).getPhoto()=="0") {


        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.itemsend, null);
        TextView name = v.findViewById(R.id.textViewitem);
        name.setText(data.get(position).getName());

        return v;
        }
        else if(data.get(position).getPhoto()=="1")
        {
            LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.itemrec, null);
            TextView name = v.findViewById(R.id.textViewitemrec);
            name.setText(data.get(position).getName());
            return  v;

        }
        else
        {
            return null;
        }
    }
    }



