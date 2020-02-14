package com.example.snakechat.view.fragment.ui.auth;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.snakechat.R;
import com.example.snakechat.data.local.MediaLoader;
import com.example.snakechat.data.local.Saveddata;
import com.example.snakechat.data.local.UserApi;
import com.example.snakechat.data.model.login.Login;
import com.example.snakechat.view.fragment.BaseFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.snakechat.data.local.ClientApi.GetClient;
import static com.example.snakechat.data.local.SharedPreferencesManger.SaveData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;

public class RegisterFragment extends BaseFragment {
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authlistener;
    Saveddata saveddata;

    Bitmap bitmap;

    String prof_pic_url = "";
    @BindView(R.id.nameed)
    EditText nameed;
    @BindView(R.id.emailed)
    EditText emailed;
    @BindView(R.id.phoneed)
    EditText phoneed;
    @BindView(R.id.passed)
    EditText passed;
    @BindView(R.id.prof_pic)
    ImageView profPic;
    @BindView(R.id.login)
    Button login;

    private String profileimageurl;
    private ArrayList<AlbumFile> mAlbumFiles = new ArrayList<>();
    private String profile;
    private UserApi userApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_login, container, false);
        ButterKnife.bind(this, root);

        userApi = GetClient().create(UserApi.class);
        saveddata = new Saveddata(getActivity());
        setSharedPreferences(getActivity());



        // Inflate the layout for this fragment
        return root;
    }


    private void openGallery() {
        Album.initialize(AlbumConfig.newBuilder(getActivity())
                .setAlbumLoader(new MediaLoader()).build());

        Album.image(this) // Image selection.
                .multipleChoice()
                .camera(true)
                .columnCount(3)
                .selectCount(1)
                .checkedList(mAlbumFiles)
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                        mAlbumFiles = result;
                        profile = result.get(0).getPath();
                        bitmap = BitmapFactory.decodeFile(profile);
                        Upload_pic(bitmap);

                        Glide.with(RegisterFragment.this).load(profile).into(profPic);

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


        StorageReference spaceRef = storageRef.child("userimages/" + nameed.getText().toString() + "_prof_pic" + ".jpg");

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
                    saveddata.hide_Dialoge();
                    Uri downloadUri = task.getResult();
                    prof_pic_url = downloadUri.toString();
//                    Toast.makeText(getActivity(), prof_pic_url, Toast.LENGTH_SHORT).show();
//                    saveddata.Save_data(nameed.getText().toString(), prof_pic_url);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }

    private void Register() {
        saveddata.show_Dialog();

        userApi.SignUp(nameed.getText().toString(), prof_pic_url, "1994-03-05", 1
                , phoneed.getText().toString(), "1994-03-05", passed.getText().toString(), passed.getText().toString(), 1).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                saveddata.hide_Dialoge();
                try {
                    if (response.body().getStatus() == 1) {


                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "try again later", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {


            }
        });
    }




    @Override
    public void onback() {
        super.onback();
    }




    @Override
    public void onResume() {
        super.onResume();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.prof_pic, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.prof_pic:
                openGallery();
                break;
            case R.id.login:
                Register();
                saveddata.Add_user(nameed.getText().toString() , emailed.getText().toString() , prof_pic_url);
                break;
        }
    }
//
}
