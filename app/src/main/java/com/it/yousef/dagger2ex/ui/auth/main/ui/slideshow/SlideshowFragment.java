package com.it.yousef.dagger2ex.ui.auth.main.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it.yousef.dagger2ex.R;
import com.it.yousef.dagger2ex.databinding.FragmentSlideshowBinding;
import com.it.yousef.dagger2ex.models.Post;
import com.it.yousef.dagger2ex.ui.auth.main.Resource;
import com.it.yousef.dagger2ex.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class SlideshowFragment extends DaggerFragment {

    //vars
    FragmentSlideshowBinding binding;
    SlideShowAdapter adapter;
    private SlideshowViewModel slideshowViewModel;
    //injects
    @Inject
    ViewModelProviderFactory providerFactory;

    //views
   // RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_slideshow,container,false);
        slideshowViewModel = ViewModelProviders.of(this,providerFactory).get(SlideshowViewModel.class);
        View root = binding.getRoot();


    //   recyclerView= binding.rc;

        slideshowViewModel.getPost().removeObservers(getViewLifecycleOwner());
        slideshowViewModel.getPost().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {

                switch (listResource.status){
                    case LOADING:
                        Toast.makeText(getActivity(), "Loading....", Toast.LENGTH_SHORT).show();
                        break;
                    case SUCCESS:
                        adapter=new SlideShowAdapter(listResource.data,getActivity());
                        binding.rc.setAdapter(adapter);
                        binding.rc.setLayoutManager(new LinearLayoutManager(getActivity()));
                        break;
                    case ERROR:
                        Toast.makeText(getActivity(), listResource.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return root;
    }
}