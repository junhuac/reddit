package me.lime.viewmodel.ui.detail;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import me.lime.viewmodel.R;
import me.lime.viewmodel.base.BaseFragment;
import me.lime.viewmodel.data.model.Post;
import me.lime.viewmodel.util.ImageLoader;
import me.lime.viewmodel.util.ViewModelFactory;

public class DetailsFragment extends BaseFragment {

    private static final String TAG = "DetailsFragment";

    @BindView(R.id.tv_post_name) TextView postNameTextView;
    @BindView(R.id.tv_post_description) TextView postDescriptionTextView;
    @BindView(R.id.tv_post_image) ImageView postThumbnailImageView;

    @Inject ViewModelFactory viewModelFactory;
    private DetailsViewModel detailsViewModel;

    @Override
    protected int layoutRes() {
        return R.layout.screen_details;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        detailsViewModel = ViewModelProviders.of(getBaseActivity(), viewModelFactory).get(DetailsViewModel.class);
        detailsViewModel.restoreFromBundle(savedInstanceState);
        displayPost();
        displayPostComments(view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        detailsViewModel.saveToBundle(outState);
    }

    private void displayPost() {
        detailsViewModel.getSelectedPost().observe(this, post -> {
            if (post != null) {
                postNameTextView.setText(post.author);
                postDescriptionTextView.setText(post.title);

                if (post.thumbnail.indexOf("http") == 0) {
                    ImageLoader.setImageFromUrl(postThumbnailImageView, post.thumbnail);
                }
            }
        });
    }

    private void displayPostComments(View view) {
        detailsViewModel.getSelectedPostComments().observe(this, comments -> {
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.tv_post_comment_layout);
            linearLayout.removeAllViews();

            int count = 0;
            for (Post post : comments) {
                count++;

                if(count > 2) break;

                // Add textview 1
                TextView textView1 = new TextView(view.getContext());
                textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView1.setText(post.author);
                textView1.setTextColor(0xff222222); // hex color 0xAARRGGBB
                linearLayout.addView(textView1);

                // Add textview 2
                TextView textView2 = new TextView(view.getContext());
                textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                textView2.setText(post.body);
                linearLayout.addView(textView2);
            }
        });
    }
}
