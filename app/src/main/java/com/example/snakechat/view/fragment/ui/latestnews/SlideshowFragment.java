package com.example.snakechat.view.fragment.ui.latestnews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snakechat.R;
import com.example.snakechat.data.adapter.NewsAdapter;
import com.example.snakechat.data.local.UserApi;
import com.example.snakechat.data.model.news.Article;
import com.example.snakechat.data.model.news.News;
import com.example.snakechat.view.fragment.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.snakechat.data.local.ClientApi.GetNews;
import static com.example.snakechat.data.local.HelperMethod.dismissProgressDialog;
import static com.example.snakechat.data.local.HelperMethod.showProgressDialog;


public class SlideshowFragment extends BaseFragment {
    LinearLayoutManager linearLayoutManager;
    NewsAdapter adapter;
    ArrayList<Article> data = new ArrayList<>();
    UserApi userApi;
    @BindView(R.id.newslist)
    RecyclerView newslist;
    @BindView(R.id.news_filbtn)
    ImageView newsFilbtn;
    @BindView(R.id.news_filteriv)
    EditText newsFilteriv;
    @BindView(R.id.news_lin1)
    LinearLayout newsLin1;
    private String date2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        ButterKnife.bind(this, root);
        setUpActivity();
        userApi = GetNews().create(UserApi.class);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        newslist.setLayoutManager(linearLayoutManager);
        adapter = new NewsAdapter(data, getActivity() , baseActivity);
        newslist.setAdapter(adapter);
        getNews();

        return root;
    }

    private void getNews() {
        showProgressDialog(getActivity() , "please wait");
        userApi.getNews("eg", "30563e9214a34b158a1a5e0a141b4d6b").enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                try {
                    dismissProgressDialog();

                    data.addAll(response.body().getArticles());
                    Toast.makeText(getActivity(), data.get(0).getUrlToImage(), Toast.LENGTH_SHORT).show();
                 adapter.notifyDataSetChanged();


                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.news_filbtn)
    public void onViewClicked() {
        data.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date2 = sdf.format(new Date());
        filterData(date2);

    }
    public void filterData(String date)
    {
        showProgressDialog(getActivity() , "please wait");

        userApi.filterNews(newsFilteriv.getText().toString() ,date , "popularity" , "30563e9214a34b158a1a5e0a141b4d6b")
                .enqueue(new Callback<News>() {
                    @Override
                    public void onResponse(Call<News> call, Response<News> response) {
                        try {
                            dismissProgressDialog();


                            data.addAll(response.body().getArticles());
                            adapter.notifyDataSetChanged();



                        } catch (Exception e) {

                        }
                        
                    }

                    @Override
                    public void onFailure(Call<News> call, Throwable t) {

                    }
                });
    }

    @Override
    public void onback() {
        super.onback();
    }
}