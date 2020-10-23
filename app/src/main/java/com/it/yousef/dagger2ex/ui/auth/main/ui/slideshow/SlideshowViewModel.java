package com.it.yousef.dagger2ex.ui.auth.main.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.it.yousef.dagger2ex.models.Post;
import com.it.yousef.dagger2ex.network.auth.main.MainApi;
import com.it.yousef.dagger2ex.ui.auth.main.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MediatorLiveData<Resource<List<Post>>> post;

    private MainApi mainApi;

    @Inject
    public SlideshowViewModel(MainApi mainApi) {
        mText = new MutableLiveData<>();

        this.mainApi=mainApi;
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Resource<List<Post>>>getPost(){

        if (post==null){

            post=new MediatorLiveData<>();
            post.setValue(Resource.loading((List<Post>)null));
            final LiveData<Resource<List<Post>>>source= LiveDataReactiveStreams
                    .fromPublisher(mainApi.getPosts()
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
                            })
                            .subscribeOn(Schedulers.io()));

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

