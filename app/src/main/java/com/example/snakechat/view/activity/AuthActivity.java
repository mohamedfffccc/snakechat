package com.example.snakechat.view.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.snakechat.R;
import com.example.snakechat.view.fragment.ui.auth.LoginFragment;
import com.example.snakechat.view.fragment.ui.auth.RegisterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.snakechat.data.local.HelperMethod.ReplaceFragment;
import static com.example.snakechat.data.local.SharedPreferencesManger.LoadData;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;

public class AuthActivity extends BaseActivity {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.button1login)
    Button button1login;
    @BindView(R.id.button2register)
    Button button2register;
    @BindView(R.id.activity_splash)
    RelativeLayout activitySplash;
    @BindView(R.id.lin1)
    RelativeLayout lin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Splash_theme);
        setContentView(R.layout.activity_splash);
        setSharedPreferences(AuthActivity.this);
        try {
            if (LoadData(AuthActivity.this , "remember").equals("true")) {
                startActivity(new Intent(AuthActivity.this , HomeActivity.class));
            }
        }
        catch ( Exception e)
        {

        }

        ButterKnife.bind(this);
        lin1.setVisibility(View.GONE);
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                lin1.setVisibility(View.VISIBLE);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                    ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(lin1 , "translationY" , 700,-1);
                    objectAnimator.setDuration(3000);
                    objectAnimator.start();
                }

            }
        }, secondsDelayed * 1000);

    }

    @OnClick({R.id.button1login, R.id.button2register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1login:
                ReplaceFragment(getSupportFragmentManager(), new LoginFragment(), R.id.activity_splash
                        , null, "medo");

                break;
            case R.id.button2register:
                ReplaceFragment(getSupportFragmentManager(), new RegisterFragment(), R.id.activity_splash
                        , null, "medo");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
