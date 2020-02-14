package com.example.snakechat.view.fragment.ui.chat;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.snakechat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.snakechat.data.local.SharedPreferencesManger.LoadData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;

public class SnakechatService extends Service {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        setSharedPreferences((Activity) getApplicationContext());
    }

//
//


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        databaseReference.child("Users").child(LoadData((Activity) getApplicationContext(), "NAME")).child("احمد").
                child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Intent intent = new Intent();
                intent.setAction("com.example.snakechat");
                intent.putExtra("message" , dataSnapshot.getValue(String.class));
                getApplicationContext().sendBroadcast(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
