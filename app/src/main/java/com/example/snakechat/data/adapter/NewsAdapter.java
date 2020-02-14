package com.example.snakechat.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.snakechat.R;
import com.example.snakechat.data.model.news.Article;
import com.example.snakechat.view.activity.BaseActivity;
import com.example.snakechat.view.fragment.ui.latestnews.NewDetailFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.snakechat.data.local.HelperMethod.ReplaceFragment;


/**
 * Created by medo on 13/11/2016.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticlesViewHolder> {


    private Context context;
    private List<Article> articleslist = new ArrayList<>();
    private BaseActivity activity;


    public NewsAdapter(List<Article> articleslist, Context context , BaseActivity activity) {
        this.articleslist = articleslist;
        this.activity=activity;
        this.context = context;

    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newitem, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {

        Article article = articleslist.get(position);
        Glide.with(context).load(article.getUrlToImage()).into(holder.newitemImage);

        holder.newitemTexttitle.setText(article.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewDetailFragment fragment = new NewDetailFragment();
                fragment.article=articleslist.get(position);
                ReplaceFragment(activity.getSupportFragmentManager(),  fragment, R.id.nav_host_fragment
            , null, "medo");

                }
        });




    }

    @Override
    public int getItemCount() {
        return articleslist.size();
    }

    public class ArticlesViewHolder extends RecyclerView.ViewHolder {
        TextView newitemLogo;
        TextView newitemDate;
        ImageView newitemImage;
        TextView newitemTexttitle;
        TextView textcon;


        public ArticlesViewHolder(@NonNull View itemView) {
            super(itemView);
            newitemTexttitle=(TextView) itemView.findViewById(R.id.newitem_texttitle);
            newitemImage=(ImageView) itemView.findViewById(R.id.newitem_image);


        }

    }


}
