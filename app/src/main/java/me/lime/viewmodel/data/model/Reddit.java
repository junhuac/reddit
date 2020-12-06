package me.lime.viewmodel.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reddit {

    public final String kind;

    public final Data data;

    public Reddit(String kind, Data data) {
        this.kind = kind;
        this.data = data;
    }
}
