package me.lime.viewmodel.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

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

public class ListViewModel extends ViewModel {

    private final PostReddit postReddit;
    private CompositeDisposable disposable;

    private final MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    private final MutableLiveData<Boolean> postLoadError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    @Inject
    public ListViewModel(PostReddit postReddit) {
        this.postReddit = postReddit;
        disposable = new CompositeDisposable();
        fetchPosts();
    }

    LiveData<List<Post>> getPosts() {
        return posts;
    }
    LiveData<Boolean> getError() {
        return postLoadError;
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }

    private void fetchPosts() {
        loading.setValue(true);
        disposable.add(postReddit.getReddit().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<Reddit>() {
                    @Override
                    public void onSuccess(Reddit reddit) {
                        final List<Children> children = reddit.data.children;
                        List<Post> value = new ArrayList<Post>();

                        for(Children ch : children)
                        {
                            if(ch.data.thumbnail.indexOf("http") == 0) {
                                value.add(ch.data);
                            }
                        }

                        postLoadError.setValue(false);
                        posts.setValue(value);
                        loading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        postLoadError.setValue(true);
                        loading.setValue(false);
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
