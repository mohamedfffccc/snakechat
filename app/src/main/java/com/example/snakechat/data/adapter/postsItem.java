package com.example.snakechat.data.adapter;

/**
 * Created by hussienalrubaye on 11/13/16.
 */

public class postsItem {
  public   String email;

    public postsItem(String email, String pro_name, String pro_img, String tw_date, String tw_text, String tw_img, String status) {
        this.email = email;
        this.pro_name = pro_name;
        this.pro_img = pro_img;
        this.tw_date = tw_date;
        this.tw_text = tw_text;
        this.tw_img = tw_img;
        this.status = status;
    }

    public String pro_name;
    public String pro_img;
    public String tw_date;
    public String tw_text;
    public String tw_img;
    public String status;

}
