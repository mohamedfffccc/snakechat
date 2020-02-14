package com.example.snakechat.view.fragment.ui.latestnews;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.snakechat.R;
import com.example.snakechat.data.model.news.Article;
import com.example.snakechat.view.activity.HomeActivity;
import com.example.snakechat.view.fragment.BaseFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.snakechat.data.local.HelperMethod.showAlert;


public class NewDetailFragment extends BaseFragment {
    public Article article;
    @BindView(R.id.newdetail_ivphoto)
    ImageView newdetailIvphoto;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.newdetai_tvtitle)
    TextView newdetaiTvtitle;
    @BindView(R.id.newdetai_tvauthor)
    TextView newdetaiTvauthor;
    @BindView(R.id.newdetai_tvdetail)
    TextView newdetaiTvdetail;
    @BindView(R.id.newdetai_tvcontent)
    TextView newdetaiTvcontent;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    String facebook = "com.facebook.katana";

    String twitter = "com.twitter.android";
    String instagram = "com.instagram.android";
    String pinterest = "com.pinterest";
    String whatsup = "com.whatsapp";
    @BindView(R.id.shareviafacebook)
    CircleImageView shareviafacebook;
    @BindView(R.id.shareviawhatsup)
    CircleImageView shareviawhatsup;
    @BindView(R.id.shareviainsta)
    CircleImageView shareviagoplus;
    @BindView(R.id.shareviatwitter)
    CircleImageView shareviatwitter;
    @BindView(R.id.shareviapinterest)
    CircleImageView shareviapinterest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_detail, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        addData();
        newdetaiTvcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                getActivity().startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    private void addData() {

        Glide.with(getActivity()).load(article.getUrlToImage()).into(newdetailIvphoto);
        newdetaiTvauthor.setText(article.getAuthor());
        newdetaiTvtitle.setText(article.getTitle());
        newdetaiTvdetail.setText(article.getDescription());
        newdetaiTvcontent.setText(article.getUrl());
        newdetaiTvcontent.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        HomeActivity homeActivity = (HomeActivity) getActivity();
        homeActivity.setTitle(article.getSource().getName());
    }

    @Override
    public void onback() {
        super.onback();
    }

    public void shareVia(String application) {
        Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(application);
        if (intent != null) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, article.getUrl());
            share.setPackage(application);
            getActivity().startActivity(share);
            Toast.makeText(getActivity(), application, Toast.LENGTH_SHORT).show();

        } else {


            showAlert("You don,t have " + application + "\n" + "Would uou want to download it ?", application, getActivity());
        }

    }

    @OnClick({R.id.shareviafacebook, R.id.shareviawhatsup, R.id.shareviainsta, R.id.shareviatwitter, R.id.shareviapinterest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shareviafacebook:
                shareVia(facebook);
//
                break;
            case R.id.shareviawhatsup:
                shareVia(whatsup);
                break;
            case R.id.shareviainsta:
                shareVia(instagram);
                break;
            case R.id.shareviatwitter:
                shareVia(twitter);
                break;
            case R.id.shareviapinterest:
                shareVia(pinterest);
                break;

        }
    }
}
