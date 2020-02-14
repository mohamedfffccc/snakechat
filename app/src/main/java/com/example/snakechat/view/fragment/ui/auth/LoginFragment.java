package com.example.snakechat.view.fragment.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.snakechat.R;
import com.example.snakechat.data.local.Saveddata;
import com.example.snakechat.data.local.UserApi;
import com.example.snakechat.data.model.login.Login;
import com.example.snakechat.view.activity.HomeActivity;
import com.example.snakechat.view.fragment.BaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.snakechat.data.local.ClientApi.GetClient;
import static com.example.snakechat.data.local.HelperMethod.ReplaceFragment;
import static com.example.snakechat.data.local.HelperMethod.dismissProgressDialog;
import static com.example.snakechat.data.local.HelperMethod.showProgressDialog;
import static com.example.snakechat.data.local.SharedPreferencesManger.SaveData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.login_edphone)
    EditText loginEdphone;
    @BindView(R.id.login_edpass)
    EditText loginEdpass;
    @BindView(R.id.login_loginbtn)
    Button loginLoginbtn;
    @BindView(R.id.login_chremember)
    CheckBox loginChremember;
    @BindView(R.id.login_ivlogo)

    ImageView loginBtnchangepass;
    private UserApi userApi;
    private Saveddata saveddata;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener authlistener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        registerApp();
        userApi = GetClient().create(UserApi.class);
        saveddata = new Saveddata(getActivity());
        setSharedPreferences(getActivity());
        loginChremember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                SaveData(getActivity(), "remember", "true");


            }
        });
//        //TODO
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("tag", "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
////                        // Log and toast
////                        String msg = getString(R.string.msg_token_fmt, token);
////                        Log.d(TAG, msg);
////                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
//        //TODO

        // Inflate the layout for this fragment
        return root;
    }

    private void Login() {
        showProgressDialog(getActivity(), "جاري تسجيل الدخول");
        userApi.LoginUser(loginEdphone.getText().toString(), loginEdpass.getText().toString()).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                dismissProgressDialog();
                try {
                    if (response.body().getStatus() == 1) {


                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        SaveData(getActivity(), "NAME", response.body().getData().getClient().getName());
                        SaveData(getActivity(), "PROFILE", response.body().getData().getClient().getEmail());
                        SaveData(getActivity(), "PHONE", response.body().getData().getClient().getPhone());

//                        Toast.makeText(getActivity(), response.body().getData().getClient().getName(), Toast.LENGTH_SHORT).show();


                        Intent HomeIntent = new Intent(getActivity(), HomeActivity.class);
                        getActivity().startActivity(HomeIntent);
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("uuu", t.getMessage());
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onback() {
        super.onback();
    }

    @OnClick({ R.id.login_loginbtn})
    public void onViewClicked(View view) {



        Login();
        }
    public void registerApp() {
        auth = FirebaseAuth.getInstance();
        authlistener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("auth", "onAuthStateChanged:signed_in:" + user.getUid());

                    Log.d("id", user.getUid());
                    SaveData(getActivity() , "USERID" , user.getUid());

                } else {
                    // User is signed out
                    Log.d("auth", "onAuthStateChanged:signed_out");
                }
            }
        };
    }
    private void signInAnonymously() {
        auth.signInAnonymously().addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("result", "signInAnonymously:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.w("res", "signInAnonymously", task.getException());


                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authlistener != null) {
            auth.removeAuthStateListener(authlistener);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authlistener);
        signInAnonymously();
    }

}




