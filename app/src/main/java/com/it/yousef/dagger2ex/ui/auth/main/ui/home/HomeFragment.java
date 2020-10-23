package com.it.yousef.dagger2ex.ui.auth.main.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it.yousef.dagger2ex.R;
import com.it.yousef.dagger2ex.databinding.FragmentHomeBinding;
import com.it.yousef.dagger2ex.models.Post;
import com.it.yousef.dagger2ex.ui.auth.main.Resource;
import com.it.yousef.dagger2ex.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class HomeFragment extends DaggerFragment {

    private static final String TAG = "HomeFragment";
    //vars
    FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private HomeBindingAdapter adapter;
    private ProgressDialog dialog;
    //inject
    @Inject
    ViewModelProviderFactory providerFactory;
    //views
    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View root = binding.getRoot();


        homeViewModel = ViewModelProviders.of(this, providerFactory).get(HomeViewModel.class);

        recyclerView = binding.rc;

        dialog=new ProgressDialog(getActivity());

       getPosts();
        return root;
    }

    private void getPosts() {
        homeViewModel.getPost().removeObservers(getViewLifecycleOwner());
        homeViewModel.getPost().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                   if (listResource!=null){

                       switch (listResource.status){
                           case LOADING:
                               dialog.show();
                               break;
                           case SUCCESS:
                               dialog.dismiss();
                               Log.d(TAG, "onChanged: sucess");
                               init(listResource.data);
                               break;
                           case ERROR:
                               Log.d(TAG, "onChanged:Error= "+listResource.message);
                           
                       }
                       
                   }
            }
        });
    }

    private void init(List<Post> listResource) {
        adapter=new HomeBindingAdapter(listResource,getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }
}