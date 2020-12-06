package me.lime.viewmodel.data.model;

import com.google.gson.annotations.SerializedName;

public class Post {

    public final String id;
    public final String name;
    public final String author;
    public final String title;
    public final String thumbnail;
    public final Preview preview;
    public final String permalink;
    public final String body;

    public Post(String id, String name, String author, String title, String thumbnail, Preview preview, String permalink, String body) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.title = title;
        this.thumbnail = thumbnail;
        this.preview = preview;
        this.permalink = permalink;
        this.body = body;
    }
}
