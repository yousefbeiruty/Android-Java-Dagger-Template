package com.it.yousef.dagger2ex.ui.auth.main.ui;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.it.yousef.dagger2ex.R;

public class GlideBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void setImageResource(ImageView imageView, int img){

        Context context=imageView.getContext();

        RequestOptions requestOptions=new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(img)
                .into(imageView);

    }
    @BindingAdapter("imageUrl")
    public static void setImageResource(ImageView imageView,String imgUrl){

        Context context=imageView.getContext();

        RequestOptions requestOptions=new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);

        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(imgUrl)
                .into(imageView);

    }
}
