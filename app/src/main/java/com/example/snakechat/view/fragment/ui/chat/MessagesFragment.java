package com.example.snakechat.view.fragment.ui.chat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.snakechat.R;
import com.example.snakechat.data.adapter.FriendItem;
import com.example.snakechat.data.adapter.MessageAdapter;
import com.example.snakechat.view.activity.HomeActivity;
import com.example.snakechat.view.fragment.BaseFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.snakechat.data.local.SharedPreferencesManger.LoadData;
import static com.example.snakechat.data.local.SharedPreferencesManger.SaveData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class MessagesFragment extends BaseFragment {
    public String username;
    @BindView(R.id.send_edsend)
    EditText sendEdsend;
    @BindView(R.id.send_sendbtn)
    ImageView sendSendbtn;
    @BindView(R.id.messagelist)
    ListView messagelist;
    @BindView(R.id.message_lin1)
    LinearLayout messageLin1;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    MessageAdapter adapter;
    ArrayList<FriendItem> data = new ArrayList<>();
    HomeActivity homeActivity;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        setSharedPreferences(getActivity());
        homeActivity=(HomeActivity) getActivity();
        homeActivity.setTitle(username);
        databaseReference.child("Users").child(LoadData(getActivity(), "NAME")).child(username).
                child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {String message = dataSnapshot.getValue(String.class);

                    FriendItem item=new FriendItem(message , "0");

                data.add(item);
                if (item.getName().equals(sendEdsend.getText().toString()))
                {
                    data.remove(item);
                    sendEdsend.setText("");
                }
//
                adapter = new MessageAdapter(data,getActivity() ,baseActivity );
                messagelist.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                messagelist.post(new Runnable() {
                    @Override
                    public void run() {
                        messagelist.smoothScrollToPosition(adapter.getCount());
                    }
                });
                }
            catch (Exception e)
            {
                Toast.makeText(getActivity() , e.getMessage() , Toast.LENGTH_SHORT).show();
            } }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        }); return root;
    }


    @OnClick(R.id.send_sendbtn)
    public void onViewClicked() {
        databaseReference.child("Users").child(LoadData(getActivity(), "NAME")).child(username).child("messages")
                .setValue(sendEdsend.getText().toString());
        databaseReference.child("Users").child(username).child(LoadData(getActivity(), "NAME")).child("messages")
                .setValue(sendEdsend.getText().toString());

        data.add(new FriendItem(sendEdsend.getText().toString() , "1"));
        adapter = new MessageAdapter(data,getActivity() ,baseActivity );
        messagelist.setAdapter(adapter);
    }
}
