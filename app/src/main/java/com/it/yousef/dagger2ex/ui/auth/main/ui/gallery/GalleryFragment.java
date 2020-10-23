package com.it.yousef.dagger2ex.ui.auth.main.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.it.yousef.dagger2ex.R;
import com.it.yousef.dagger2ex.models.Post;
import com.it.yousef.dagger2ex.ui.auth.main.Resource;
import com.it.yousef.dagger2ex.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;


public class GalleryFragment extends DaggerFragment {

    private static final String TAG = "GalleryFragment";
    //vars
    private GalleryViewModel galleryViewModel;
    private ProgressDialog dialog;
    private GalleryAdapter adapter;

    //views
    private RecyclerView recyclerView;


    //injects
    @Inject
    ViewModelProviderFactory providerFactory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this, providerFactory).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);

        recyclerView=root.findViewById(R.id.rc);
        dialog=new ProgressDialog(getActivity());
        observe();

        return root;
    }



    private void observe() {
        galleryViewModel.getText().removeObservers(getViewLifecycleOwner());
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {
                if (listResource!=null){
                    switch (listResource.status){
                        case LOADING:
                            dialog.show();
                            Log.d(TAG, "onChanged: loading...");

                            break;
                        case SUCCESS:
                            dialog.dismiss();

                            adapter=new GalleryAdapter(getActivity(),listResource.data);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                            break;


                        case ERROR:
                            dialog.dismiss();
                            Log.e(TAG, "onChanged:ERROR= "+listResource.message );
                            break;
                    }
                }
            }
        });
    }
}