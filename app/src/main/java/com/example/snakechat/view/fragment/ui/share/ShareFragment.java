package com.example.snakechat.view.fragment.ui.share;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.snakechat.R;
import com.example.snakechat.view.activity.AuthActivity;
import com.example.snakechat.view.activity.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.snakechat.data.local.SharedPreferencesManger.clean;
import static com.example.snakechat.data.local.SharedPreferencesManger.setSharedPreferences;


public class ShareFragment extends Fragment {

    @BindView(R.id.button)
    Button button;
    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_share, container, false);
        ButterKnife.bind(this, root);
        setSharedPreferences(getActivity());

        return root;
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        show();
    }
    public void show()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity())
                .setTitle("تحذير")
                .setMessage("انت علي وشك تسجيل الخروج")
                .setIcon(R.drawable.ic_power_settings_new_black_24dp);
        builder.setPositiveButton("متابعة", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().startActivity(new Intent(getActivity(), AuthActivity.class));
                clean(getActivity());

            }
        })
                .setNegativeButton("رجوع", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().startActivity(new Intent(getActivity(), HomeActivity.class));
                    }
                });
        builder.show();
    }
}