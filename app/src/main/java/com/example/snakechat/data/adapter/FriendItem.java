package com.example.snakechat.data.adapter;

public class FriendItem {
    String name;

    public FriendItem(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
