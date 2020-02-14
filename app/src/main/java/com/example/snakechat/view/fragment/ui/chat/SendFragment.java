package com.example.snakechat.view.fragment.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.snakechat.R;
import com.example.snakechat.data.adapter.FriendItem;
import com.example.snakechat.data.adapter.FriendsAdapter;
import com.example.snakechat.data.local.Saveddata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.snakechat.data.local.HelperMethod.ReplaceFragment;
import static com.example.snakechat.data.local.SharedPreferencesManger.LoadData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;


public class SendFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @BindView(R.id.listfriends)
    ListView listfriends;
    private Saveddata saveddata;
    ArrayList<FriendItem> friends = new ArrayList<>();
    FriendsAdapter adapter;
    String photo_url;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_send, container, false);
        ButterKnife.bind(this, root);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        setSharedPreferences(getActivity());
        adapter = new FriendsAdapter(friends, getActivity());

        listfriends.setAdapter(adapter);
        saveddata = new Saveddata(getActivity());
        saveddata.Read_data();
        getMyFriends();
        listfriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessagesFragment messagesFragment=new MessagesFragment();
                ReplaceFragment(getActivity().getSupportFragmentManager(), messagesFragment, R.id.nav_host_fragment
            , null, "medo");
                messagesFragment.username=friends.get(position).getName();
            }
        });
//
        return root;

    }

    public void getMyFriends() {

        databaseReference.child("Users").child(LoadData(getActivity() , "NAME")).child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    friends.clear();

                    HashMap<String, String> followers = (HashMap<String, String>) dataSnapshot.getValue();
                    for (Map.Entry<String, String> followentry : followers.entrySet()) {
                        String following_name = followentry.getKey();
//                        friends.add(new FriendItem(following_name , "5"));
                        if (!following_name.equals(LoadData(getActivity() , "NAME"))) {
                            getPhoto(following_name );

                        }




                    }
//                    for (int i = 0; i < friends.size(); i++) {
//                        if (friends.get(i).getName().equals(LoadData(getActivity() , "NAME"))) {
//                            friends.remove(i);
//                            adapter.notifyDataSetChanged();
//                        }
//
//                    }




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
    public void getPhoto(String name )
    {
        databaseReference.child("Users").child(name).child("profile picture")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        photo_url=dataSnapshot.getValue(String.class);
//                        friends.clear();
                        friends.add(new FriendItem(name , photo_url));
                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}