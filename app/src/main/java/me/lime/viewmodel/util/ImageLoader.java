package me.lime.viewmodel.util;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ImageLoader {

    public static void setImageFromUrl(final ImageView imageView, final String urlString) {

        Observable.just(urlString)
                .map(new Function<String, Drawable>() {
                    @Override public Drawable apply(String s) throws Exception {
                        URL url = null;
                        try {
                            url = new URL(s);
                            return Drawable.createFromStream((InputStream) url.getContent(), "profile");
                        } catch (final IOException ex) {
                            return null;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Drawable>() {
                    @Override public void accept(Drawable drawable) throws Exception {
                        imageView.setImageDrawable(drawable);
                    }
                });
    }
}
