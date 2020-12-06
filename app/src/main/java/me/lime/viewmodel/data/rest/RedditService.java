package me.lime.viewmodel.data.rest;

import java.util.List;

import io.reactivex.Single;
import me.lime.viewmodel.data.model.Post;
import me.lime.viewmodel.data.model.Reddit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RedditService {

    @GET("r/travel/new.json")
    Single<Reddit> getReddit();

    @GET
    Single<List<Reddit>> getPost(@Url String url);

    @GET("r/travel/comments/{user}/{post}.json")
    Single<Post> getPost(@Path("user") String user, @Path("post") String post);
}
