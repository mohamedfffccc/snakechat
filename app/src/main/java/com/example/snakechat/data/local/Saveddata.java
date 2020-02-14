package com.example.snakechat.data.local;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;

import com.example.snakechat.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by medo on 07/04/2017.
 */

public class Saveddata {
    static Typeface typeface;
    SharedPreferences pref;
    static Context con;
        FirebaseDatabase database;
        DatabaseReference databaseReference;
    public String name;
    public String prof_pic="";

    ProgressDialog progressDialog;
    HashSet<String> set;
    public Saveddata(Context con)
    {
        this.con=con;
        pref = con.getSharedPreferences("mypref" , Context.MODE_PRIVATE);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }
    public void Add_user(String name , String email,String photo)
    {
        databaseReference.child("Users").child(name).setValue(email);
        databaseReference.child("Users").child(name).child("profile picture").setValue(photo);
        databaseReference.child("Users").child(name).child("followers").child(name).setValue("i follow");
//        databaseReference.child("Users").child(name).child("messages").child(name).setValue("i follow");
    }
    //TODO save data  in shared preference
    public void Save_data(String u_name , String url)
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name" , u_name);
        editor.putString("url" , url);
        Log.d("data" , "saved");
        editor.commit();
    }
    //TODO read data from shared preference
    public void Read_data()
    {
        name = pref.getString("name" , "0");
        prof_pic=pref.getString("url" , "0");
        Log.d("data" , name+"\n"+prof_pic);
//        if (name.equals("0"))
//        {
//            Intent i = new Intent(con, RegisterFragment.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            con.startActivity(i);
//        }
    }
    //TODO upload profile picture to firebase

    public void show_Dialog()
    {
        progressDialog = new ProgressDialog(con);
        progressDialog.setTitle("please wait");
        progressDialog.setIcon(R.drawable.tweets);
        progressDialog.show();
    }
    public void hide_Dialoge()
    {
        progressDialog.dismiss();
    }
    public  void Save_Followings(ArrayList<String> f)
    {
         set = new HashSet<String>();
        set.addAll(f);

        SharedPreferences.Editor editor2 = pref.edit();
        editor2.putStringSet("followings" , set);
        Log.d("set" , "saved");
        editor2.commit();
    }
    public  void  Read_Followings()
    {
        set = (HashSet<String>) pref.getStringSet("followings" , null);
    }



}