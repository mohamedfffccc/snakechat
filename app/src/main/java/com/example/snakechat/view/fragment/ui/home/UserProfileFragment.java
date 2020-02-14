package com.example.snakechat.view.fragment.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.snakechat.R;
import com.example.snakechat.data.adapter.postsItem;
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
import static com.example.snakechat.data.local.SharedPreferencesManger.SaveData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;

public class UserProfileFragment extends BaseFragment {
    public String u_name;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    ArrayList<postsItem> posts;
    UserProfileAdapter adapter;
    ListView userprofilelist;
    String name,pro_pic,post_txt,post_img,date;
    Saveddata saveddata;
    String pro_pict;
    int index = 0;
    String myname;
    ArrayList<String> followings;
    ImageView follow;
    boolean isfollower=false;
String profile_picture;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_profile, container, false);
       // setUpActivity();
        userprofilelist = (ListView) root.findViewById(R.id.userprofile_list);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        setSharedPreferences(getActivity());
        saveddata = new Saveddata(getActivity());
        saveddata.Read_data();
        myname = LoadData(getActivity() , "NAME");
        followings = new ArrayList<String>();
        posts = new ArrayList<postsItem>();
        adapter= new UserProfileAdapter(posts,getActivity());
        u_name=LoadData(getActivity() , "username");
        getProfilePic();
        userprofilelist.setAdapter(adapter);










        return root;
    }
    public void  getProfilePic()
    {
        databaseReference.child("Users").child(u_name).child("profile picture").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot!=null) {
                        profile_picture = dataSnapshot.getValue(String.class);
                        posts.add(new postsItem(null,u_name,profile_picture,null,null,null,"prof"));
                        Add_data();
                        adapter.notifyDataSetChanged();
                    }
                    else
                    {
                        Log.d("firebase" , dataSnapshot.getValue(String.class));
                    }


                    }

                catch (Exception e)
                {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public  void Add_data()
    {
        databaseReference.child("posts").child(u_name).addValueEventListener(new ValueEventListener() {
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

                        posts.add(new postsItem(null,u_name,pro_pic,date,post_txt,post_img,"data"));
                        adapter.notifyDataSetChanged();
                        SaveData(getActivity(), "username", null);
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

    @Override
    public void onback() {
        super.onback();
    }

    public class UserProfileAdapter extends BaseAdapter
    {
        ArrayList<postsItem> data;
        Context context;


        public UserProfileAdapter(ArrayList<postsItem> data , Context context)
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
                checkIsFollower();


                View myview = inflater.inflate(R.layout.profile2 , null);
                postsItem item = data.get(i);
                setSharedPreferences(getActivity());


                ImageView img_prof = (ImageView) myview.findViewById(R.id.image_prof);
                Glide.with(context).load(item.pro_img).into(img_prof);
                final TextView tv_name = (TextView) myview.findViewById(R.id.textname);
                tv_name.setText(item.pro_name);
                TextView tv_email = (TextView) myview.findViewById(R.id.textemail);

                follow=(ImageView) myview.findViewById(R.id.addfollower_btn);

                follow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (isfollower == false) {

                            Add_Follower();
                            follow.setImageResource(R.drawable.ic_person_black_24dp);


                            isfollower = true;


                        } else if ((isfollower == true)) {

                            Remove_follower();
                            follow.setImageResource(R.drawable.ic_person_add_black_24dp);
                            isfollower=false;


                        }
                    }
                });

                return  myview;
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
    public void  Add_Follower()
    {
        databaseReference.child("Users").child(myname).child("followers").child(u_name).setValue("i follow");
        databaseReference.child("Users").child(LoadData(getActivity(), "NAME")).child(u_name).child("messages")
                .setValue("");
        databaseReference.child("Users").child(u_name).child(LoadData(getActivity(), "NAME")).child("messages")
                .setValue("");
        Toast.makeText(getActivity() , u_name + " is a follower" , Toast.LENGTH_SHORT).show();

    }
    public void Remove_follower()
    {
        databaseReference.child("Users").child(myname).child("followers").child(u_name).removeValue();
        Toast.makeText(getActivity() , u_name + " is a unfollower" , Toast.LENGTH_SHORT).show();
    }
    public void  checkIsFollower()
    {
        try {


        databaseReference.child("Users").child(myname).child("followers").child(u_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.getValue(String.class).equals("i follow")) {
                        isfollower=true;
                        follow.setImageResource(R.drawable.ic_person_black_24dp);


                    }
                    else
                    {
                        isfollower=false;
                        follow.setImageResource(R.drawable.ic_person_add_black_24dp);

                    }

                }
                catch (Exception e)
                {

                }  }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }
        catch (Exception e)
        {

        }
    }





}
