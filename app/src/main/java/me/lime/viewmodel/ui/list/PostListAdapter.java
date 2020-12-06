package me.lime.viewmodel.ui.list;

import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.lime.viewmodel.R;
import me.lime.viewmodel.data.model.Post;
import me.lime.viewmodel.util.ImageLoader;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>{

    private PostSelectedListener postSelectedListener;
    private final List<Post> data = new ArrayList<>();

    PostListAdapter(ListViewModel viewModel, LifecycleOwner lifecycleOwner, PostSelectedListener postSelectedListener) {
        this.postSelectedListener = postSelectedListener;
        viewModel.getPosts().observe(lifecycleOwner, posts -> {
            data.clear();
            if (posts != null) {
                data.addAll(posts);
                notifyDataSetChanged();
            }
        });
        setHasStableIds(true);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post_list_item, parent, false);
        return new PostViewHolder(view, postSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position; // data.get(position).id;
    }

    static final class PostViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_post_name) TextView postNameTextView;
        @BindView(R.id.tv_post_description) TextView postDescriptionTextView;
        @BindView(R.id.tv_post_image) ImageView postThumbnailImageView;

        private Post post;

        PostViewHolder(View itemView, PostSelectedListener postSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if(post != null) {
                    postSelectedListener.onPostSelected(post);
                }
            });
        }

        void bind(Post post) {
            this.post = post;
            postNameTextView.setText(post.author);
            postDescriptionTextView.setText(post.title);

            if(post.thumbnail.indexOf("http") == 0) {
                ImageLoader.setImageFromUrl(postThumbnailImageView, post.thumbnail);
            }
        }
    }
}
