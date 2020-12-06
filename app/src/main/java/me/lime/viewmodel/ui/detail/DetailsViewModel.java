package me.lime.viewmodel.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import me.lime.viewmodel.data.model.Children;
import me.lime.viewmodel.data.model.Post;
import me.lime.viewmodel.data.model.Reddit;
import me.lime.viewmodel.data.rest.PostReddit;

public class DetailsViewModel extends ViewModel {

    private final PostReddit postReddit;
    private CompositeDisposable disposable;

    private final MutableLiveData<Post> selectedPost = new MutableLiveData<>();

    public LiveData<Post> getSelectedPost() {
        return selectedPost;
    }

    private final MutableLiveData<List<Post>> selectedPostComments = new MutableLiveData<>();

    public LiveData<List<Post>> getSelectedPostComments() {
        return selectedPostComments;
    }

    @Inject
    public DetailsViewModel(PostReddit postReddit) {
        this.postReddit = postReddit;
        disposable = new CompositeDisposable();
    }

    public void setSelectedPost(Post post) {
        selectedPost.setValue(post);
    }

    public void saveToBundle(Bundle outState) {
        if(selectedPost.getValue() != null) {
            outState.putStringArray("post_details", new String[] {
                    selectedPost.getValue().id,
                    selectedPost.getValue().permalink
            });
        }
    }

    public void restoreFromBundle(Bundle savedInstanceState) {
        if(selectedPost.getValue() == null) {
            if(savedInstanceState != null && savedInstanceState.containsKey("post_details")) {
                loadPost(savedInstanceState.getStringArray("post_details"));
            }
        }
        if(selectedPost != null) {
            String url = selectedPost.getValue().permalink;
            url = url.substring(0, url.length()-2);
            url = url.replaceAll("/%2F/", "");
            url += ".json";
            loadPostComments(url);
        }
    }

    private void loadPost(String[] post_details) {
        disposable.add(postReddit.getPost(post_details[0], post_details[1]).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Post>() {
            @Override
            public void onSuccess(Post value) {
                selectedPost.setValue(value);
            }

            @Override
            public void onError(Throwable e) {

            }
        }));
    }

    private void loadPostComments(String permalink) {
        disposable.add(postReddit.getPost(permalink).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<Reddit>>() {
                    @Override
                    public void onSuccess(List<Reddit> value) {
                        List<Post> comments = new ArrayList<Post>();

                        if(value.size() > 1) {
                            for (Children child : value.get(1).data.children) {
                                comments.add(child.data);
                            }
                        }

                        selectedPostComments.setValue(comments);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.clear();
            disposable = null;
        }
    }
}
