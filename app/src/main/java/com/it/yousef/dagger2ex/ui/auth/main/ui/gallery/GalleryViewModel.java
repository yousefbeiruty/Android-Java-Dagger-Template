package com.it.yousef.dagger2ex.ui.auth.main.ui.gallery;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.it.yousef.dagger2ex.ui.auth.main.Resource;
import com.it.yousef.dagger2ex.SessionManager;
import com.it.yousef.dagger2ex.models.Post;
import com.it.yousef.dagger2ex.network.auth.main.MainApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GalleryViewModel extends ViewModel {

    private static final String TAG = "GalleryViewModel";
    private MediatorLiveData<Resource<List<Post>>> post;

    //inject
    private MainApi mainApi;
    private SessionManager sessionManager;

    @Inject
    public GalleryViewModel(SessionManager sessionManager, MainApi mainApi) {
        this.sessionManager = sessionManager;
        this.mainApi = mainApi;

        Log.d(TAG, "GalleryViewModel:working.... ");

    }

    public LiveData<Resource<List<Post>>> getText() {
        if (post == null) {
            post = new MediatorLiveData<>();
            post.setValue(Resource.loading((List<Post>) null));
            final LiveData<Resource<List<Post>>> source = LiveDataReactiveStreams
                    .fromPublisher(mainApi.getPostsFromUser(sessionManager.getAuthUser().getValue().data.getId())
                            .onErrorReturn(new Function<Throwable, List<Post>>() {
                                @Override
                                public List<Post> apply(Throwable throwable) throws Exception {
                                    Post post = new Post();
                                    post.setId(-1);
                                    ArrayList<Post> arrayList = new ArrayList<>();
                                    arrayList.add(post);
                                    return arrayList;
                                }
                            }).map(new Function<List<Post>, Resource<List<Post>>>() {
                                @Override
                                public Resource<List<Post>> apply(List<Post> posts) throws Exception {
                                    if (posts.size() > 0) {

                                        if (posts.get(0).getId()==-1){
                                            return Resource.error("Something went wrong",null);
                                        }
                                    }

                                    return Resource.success(posts);
                                }
                            }).subscribeOn(Schedulers.io()));

            post.addSource(source, new Observer<Resource<List<Post>>>() {
                @Override
                public void onChanged(Resource<List<Post>> listResource) {
                post.setValue(listResource);
                post.removeSource(source);
                }
            });
        }
        return post;
    }
}