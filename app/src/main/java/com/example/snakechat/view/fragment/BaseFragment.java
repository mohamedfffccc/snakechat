package com.example.snakechat.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.snakechat.view.activity.BaseActivity;


public class BaseFragment extends Fragment {
    public BaseActivity baseActivity;

    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onback() {
        baseActivity.superBackPressed();
    }


}
