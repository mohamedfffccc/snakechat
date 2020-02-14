package com.example.snakechat.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.snakechat.R;
import com.example.snakechat.data.adapter.FriendItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends BaseAdapter {
    ArrayList<FriendItem> data;
    Context con;

    public FriendsAdapter(ArrayList<FriendItem> data, Context con) {
        this.data = data;
        this.con = con;
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
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.friend_item, null);
        TextView name = v.findViewById(R.id.friend_name);
        name.setText(data.get(position).name);
        CircleImageView photo = v.findViewById(R.id.friend_image);
        Glide.with(con).load(data.get(position).photo).into(photo);
        return v;
    }


}
