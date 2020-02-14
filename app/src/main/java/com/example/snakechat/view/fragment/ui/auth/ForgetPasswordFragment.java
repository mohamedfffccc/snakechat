package com.example.snakechat.view.fragment.ui.auth;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.example.snakechat.R;
import com.example.snakechat.data.local.UserApi;
import com.example.snakechat.data.model.login.Login;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.snakechat.data.local.ClientApi.GetClient;
import static com.example.snakechat.data.local.HelperMethod.dismissProgressDialog;
import static com.example.snakechat.data.local.HelperMethod.showProgressDialog;

public class ForgetPasswordFragment extends Fragment {
    UserApi userApi = GetClient().create(UserApi.class);
    @BindView(R.id.forget_edphone)
    EditText forgetEdphone;
    @BindView(R.id.forget_btnnext)
    Button forgetBtnnext;
    private NotificationManager manager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forget_password, container, false);
        ButterKnife.bind(this, root);

        // Inflate the layout for this fragment
        return root;

    }

    private void resttPass() {
        showProgressDialog(getActivity(), "please wait");
        userApi.restrPass(forgetEdphone.getText().toString()).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                dismissProgressDialog();
                try {
                    if (response.body().getStatus()==1) {
                        Notify(response.body().getData().getPinCodeForTest());

                    }

                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.forget_btnnext)
    public void onViewClicked() {
        resttPass();
    }
    public void Notify(int key) {
        int id = 0;
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setContentTitle(getActivity().getString(R.string.app_name))
                .setContentText(String.valueOf(key) + " هو كود استرجاع كلمة المرور")
                .setSmallIcon(R.drawable.snakelogoic);
        builder.setCategory("رسالة تاكيد");

        manager = (NotificationManager) getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);
        manager.notify(id, builder.build());
        id++;
    }

}
