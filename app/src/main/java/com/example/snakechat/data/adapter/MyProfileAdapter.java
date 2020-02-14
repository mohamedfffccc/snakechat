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

import java.util.ArrayList;

public class MyProfileAdapter extends BaseAdapter
{
    ArrayList<postsItem> data;
    Context context;


    public MyProfileAdapter(ArrayList<postsItem> data , Context context)
    {
        this.data=data;
        this.context=context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        postsItem adapterItems = data.get(i);
        if (adapterItems.status.equals("prof"))
        {

            View myview = inflater.inflate(R.layout.profile , null);
            postsItem item = data.get(i);
            ImageView img_prof = (ImageView) myview.findViewById(R.id.image_prof);
            Glide.with(context).load(item.pro_img).into(img_prof);
            TextView tv_name = (TextView) myview.findViewById(R.id.textname);
            tv_name.setText(item.pro_name);
            TextView tv_email = (TextView) myview.findViewById(R.id.textemail);
            tv_email.setText(item.email);


            return  myview;
        }
        else if (adapterItems.status.equals("loading"))
        {
            View myview = inflater.inflate(R.layout.tweet_loading , null);
            return myview;
        }
        else if (adapterItems.status.equals("data"))
        {
            View myview = inflater.inflate(R.layout.tweet_item , null);
            TextView username = (TextView) myview.findViewById(R.id.txtUserName);
            username.setText(adapterItems.pro_name);
            TextView postdate = (TextView) myview.findViewById(R.id.txt_tweet_date);
            postdate.setText(adapterItems.tw_date);
            TextView posttext = (TextView) myview.findViewById(R.id.txt_tweet);
            posttext.setText(adapterItems.tw_text);
            ImageView pic_name = (ImageView) myview.findViewById(R.id.picture_path);
            Glide.with(context).load(adapterItems.pro_img).into(pic_name);
            ImageView post_img = (ImageView) myview.findViewById(R.id.tweet_picture);
            Glide.with(context).load(adapterItems.tw_img).into(post_img);

            return myview;
        }
        else {

            return null;
        }

    }


}