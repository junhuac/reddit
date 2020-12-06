package me.lime.viewmodel.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Children {

    public final String kind;
    public final Post data;

    public Children(String kind, Post data) {
        this.kind = kind;
        this.data = data;
    }
}
