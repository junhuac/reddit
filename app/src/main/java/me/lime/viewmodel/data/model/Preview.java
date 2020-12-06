package me.lime.viewmodel.data.model;

import java.util.List;

public class Preview {

    public final List<Image> images;
    public final boolean enabled;

    public Preview(List<Image> images, boolean enabled) {
        this.images = images;
        this.enabled = enabled;
    }
}
