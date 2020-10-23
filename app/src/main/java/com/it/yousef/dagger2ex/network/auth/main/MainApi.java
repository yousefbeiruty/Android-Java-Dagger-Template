package com.it.yousef.dagger2ex.network.auth.main;

import com.it.yousef.dagger2ex.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {

    @GET("posts")
    Flowable<List<Post>>getPostsFromUser(@Query("userId")int id);

    @GET("posts")
    Flowable<List<Post>>getPosts();
}
