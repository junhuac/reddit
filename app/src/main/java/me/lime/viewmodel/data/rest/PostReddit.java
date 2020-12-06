package me.lime.viewmodel.data.rest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import me.lime.viewmodel.data.model.Children;
import me.lime.viewmodel.data.model.Post;
import me.lime.viewmodel.data.model.Reddit;

public class PostReddit {

    private final RedditService redditService;

    @Inject
    public PostReddit(RedditService redditService) {
        this.redditService = redditService;
    }

    public Single<Reddit> getReddit() {
        return redditService.getReddit();
    }

    public Single<Post> getPost(String user, String post) {
        return redditService.getPost(user, post);
    }

    public Single<List<Reddit>> getPost(String post) {
        return redditService.getPost(post);
    }
}
