package me.lime.viewmodel.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    public final List<Children> children;

    public Data(List<Children> children) {
        this.children = children;
    }
}
