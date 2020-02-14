package com.example.snakechat.view.fragment.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.snakechat.R;
import com.example.snakechat.data.adapter.postsItem;
import com.example.snakechat.data.local.MediaLoader;
import com.example.snakechat.data.local.Saveddata;
import com.example.snakechat.data.model.login.Client;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.snakechat.data.local.SharedPreferencesManger.LoadData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;


public class HomeFragment extends Fragment {

    @BindView(R.id.home_tweetlist)
    ListView homeTweetlist;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Saveddata saveddata;
    ArrayList<postsItem> posts;
    ArrayList<String> followings;
    String name, pro_pic, post_txt, post_img, date;
    MyPostsAdapter adapter;
    public Client client;


    ImageView buttonAddPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        saveddata = new Saveddata(getActivity());
        saveddata.Read_data();
        setSharedPreferences(getActivity());

        posts = new ArrayList<>();
        followings = new ArrayList<String>();
        posts.add(new postsItem(null, null, null,
                null, null, null, "add"));

        posts.add(new postsItem(null, null, null, null, null, null, "loading"));
        Getmyfollowers();
        adapter = new MyPostsAdapter(posts, getActivity());
        homeTweetlist.setAdapter(adapter);

        return root;
    }

    public void Getmyfollowers() {

        databaseReference.child("Users").child(LoadData(getActivity(), "NAME")).child("followers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {


                    followings.clear();

                    HashMap<String, String> followers = (HashMap<String, String>) dataSnapshot.getValue();
                    for (Map.Entry<String, String> followentry : followers.entrySet()) {
                        String following_name = followentry.getKey();
                        followings.add(following_name);
//

                        Log.d("followings", followings.toString());


                    }
                } catch (Exception e) {

                }
                Add_Posts();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void Add_Posts() {
        posts.clear();
        posts.add(new postsItem(null, null,
                null, null, null, null, "add"));

        for (final String f : followings) {

            databaseReference.child("posts").child(f).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    HashMap<String, Object> h1 = (HashMap<String, Object>) dataSnapshot.getValue();
                    if (h1 != null) {
                        for (Map.Entry<String, Object> en1 : h1.entrySet()) {
                            HashMap<String, String> h2 = (HashMap<String, String>) en1.getValue();
                            date = h2.get("date");
                            post_img = h2.get("post_img");
                            post_txt = h2.get("post_txt");
                            pro_pic = h2.get("pro_pic");

                            posts.add(new postsItem(null, f, pro_pic, date, post_txt, post_img, "data"));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public class MyPostsAdapter extends BaseAdapter {
        ArrayList<postsItem> data;
        Context context;
        private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
        private String posturl;
        private Bitmap bitmap;
        private String post_pic_url;
        private String date2;
        ImageView post;
        private boolean isliked = false;
        private ImageView like;

        public MyPostsAdapter(ArrayList<postsItem> data, Context context) {
            this.data = data;
            this.context = context;
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


            if (adapterItems.status.equals("loading")) {
                View myview = inflater.inflate(R.layout.tweet_loading, null);
                return myview;
            } else if (adapterItems.status.equals("add")) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
                date2 = sdf.format(new Date());

                View myview = inflater.inflate(R.layout.fragment_add_post, null);
                EditText posted = (EditText) myview.findViewById(R.id.etPost);
                ImageView attach = (ImageView) myview.findViewById(R.id.iv_attach);


                ImageView postimage = (ImageView) myview.findViewById(R.id.iv_post);
                attach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();
                    }
                });
                postimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseReference.child("posts").child(LoadData(getActivity(), "NAME")).child(date2).child("date").setValue(date2);
                        databaseReference.child("posts").child(LoadData(getActivity(), "NAME")).child(date2).child("post_txt").setValue(posted.getText().toString());
                        databaseReference.child("posts").child(LoadData(getActivity(), "NAME")).child(date2).child("post_img").setValue(post_pic_url);
                        databaseReference.child("posts").child(LoadData(getActivity(), "NAME")).child(date2).child("pro_pic").setValue(LoadData(getActivity(), "PROFILE"));
                        Getmyfollowers();

                    }
                });
                return myview;
            } else if (adapterItems.status.equals("data")) {
                checkIsLiked(data.get(i).pro_name, data.get(i).tw_date);
                View myview = inflater.inflate(R.layout.tweet_item, null);
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
                like = (ImageView) myview.findViewById(R.id.iv_like);
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//

                        if (isliked == false) {
                            likePost(data.get(i).pro_name, data.get(i).tw_date);


                            like.setImageResource(R.drawable.ic_liked);


                            isliked = true;


                        } else if ((isliked == true)) {


                            disLikePost(data.get(i).pro_name, data.get(i).tw_date);
                            like.setImageResource(R.drawable.like);
                            isliked=false;


                        }
                        Getmyfollowers();

                    }
                });

                return myview;
            } else {

                return null;
            }
        }

        private void likePost(String pro_name, String tw_date) {
            databaseReference.child("posts").child(pro_name).child(tw_date).child("likes")
                    .child(LoadData(getActivity(), "NAME")).setValue("liked");
            Toast.makeText(context , "Liked" , Toast.LENGTH_SHORT).show();
        }

        private void disLikePost(String pro_name, String tw_date) {
            databaseReference.child("posts").child(pro_name).child(tw_date).child("likes")
                    .child(LoadData(getActivity(), "NAME")).setValue("disliked");
            Toast.makeText(context , "Disliked" , Toast.LENGTH_SHORT).show();
        }

        private void openGallery() {
            Album.initialize(AlbumConfig.newBuilder(getActivity())
                    .setAlbumLoader(new MediaLoader()).build());

            Album.image(context) // Image selection.
                    .multipleChoice()
                    .camera(true)
                    .columnCount(3)
                    .selectCount(1)
                    .checkedList(mAlbumFiles)
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(@NonNull ArrayList<AlbumFile> result) {
                            mAlbumFiles = result;
                            posturl = result.get(0).getPath();
                            bitmap = BitmapFactory.decodeFile(posturl);
                            Upload_pic(bitmap);


                        }
                    })
                    .onCancel(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {
                        }
                    })
                    .start();
        }

        public void Upload_pic(Bitmap bitmap) {
            saveddata.show_Dialog();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://snakechat-da349.appspot.com/");
            saveddata.Read_data();

            StorageReference spaceRef = storageRef.child("userimages/" + LoadData(getActivity(), "NAME") + System.currentTimeMillis() + "_prof_pic" + ".jpg");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();
            UploadTask uploadTask = spaceRef.putBytes(data);
            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return spaceRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        post_pic_url = downloadUri.toString();
//                        Toast.makeText(getActivity(), post_pic_url, Toast.LENGTH_SHORT).show();
                        saveddata.hide_Dialoge();

                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
//
        }

        public void checkIsLiked(String pro_name, String tw_date) {
            try {


            databaseReference.child("posts").child(pro_name).child(tw_date).child("likes")
                    .child(LoadData(getActivity(), "NAME")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        if (dataSnapshot.getValue(String.class).equals("liked")) {
                            isliked=true;
                            like.setImageResource(R.drawable.ic_liked);


                        }
                        else
                        if (dataSnapshot.getValue(String.class).equals("disliked"))
                        {
                            isliked=false;
                            like.setImageResource(R.drawable.like);

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
            catch (Exception e)
            {

            }

        }
    }
}
        // isliked=true;
//                                like.setImageResource(R.drawable.ic_favorite_black_24dp);
//
//
//                            }
//                            else
//                            {
//                                isliked=false;
//                                like.setImageResource(R.drawable.ic_favorite_border_black_24dp);

