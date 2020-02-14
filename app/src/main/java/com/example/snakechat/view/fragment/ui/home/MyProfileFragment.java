package com.example.snakechat.view.fragment.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.snakechat.R;
import com.example.snakechat.data.adapter.postsItem;
import com.example.snakechat.data.adapter.MyProfileAdapter;
import com.example.snakechat.data.local.Saveddata;
import com.example.snakechat.view.fragment.BaseFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.snakechat.data.local.SharedPreferencesManger.LoadData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;


public class MyProfileFragment extends BaseFragment {
    String u_name;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<postsItem> posts;

    MyProfileAdapter adapter;
    ListView list;
    String name,pro_pic,post_txt,post_img,date;
    Saveddata saveddata;
    String pro_pict;
    int index = 0;
    String myname;
    ArrayList<String> followings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        setUpActivity();
        list=(ListView) root.findViewById(R.id.my_tweets);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        saveddata = new Saveddata(getActivity());
        setSharedPreferences(getActivity());
        saveddata.Read_data();
        myname = LoadData(getActivity() , "NAME");
        posts = new ArrayList<postsItem>();
        adapter= new MyProfileAdapter(posts,getActivity());
        posts.add(new postsItem(LoadData(getActivity() , "EMAIL") ,LoadData(getActivity() , "NAME"),LoadData(getActivity() , "PROFILE"),null,null,null,"prof"));

        Add_data();
        list.setAdapter(adapter);
        return root;
    }
    public  void Add_data()
    {
        try{
            databaseReference.child("posts").child(myname).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {


                    HashMap<String, Object> posts2 = (HashMap<String, Object>) dataSnapshot.getValue();
                    //Log.d("posts" , posts2.toString());
                    for (Map.Entry<String, Object> entry:posts2.entrySet()) {

                        //Log.d("name" , name);
                        HashMap<String, String> posts_text = (HashMap<String, String>) entry.getValue();
                        date =  posts_text.get("date");
                        post_img =  posts_text.get("post_img");
                        post_txt =  posts_text.get("post_txt");
                        pro_pic =  posts_text.get("pro_pic");

                        posts.add(new postsItem(LoadData(getActivity() , "EMAIL") ,LoadData(getActivity() , "NAME"),LoadData(getActivity() , "PROFILE"),date,post_txt,post_img,"data"));
                        adapter.notifyDataSetChanged();
                    }
                    }
                    catch (Exception e)
                    {

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {

        }

    }

    @Override
    public void onback() {
        super.onback();
    }
}

