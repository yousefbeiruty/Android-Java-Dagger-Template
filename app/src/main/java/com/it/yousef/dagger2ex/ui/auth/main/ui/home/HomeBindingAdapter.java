package com.it.yousef.dagger2ex.ui.auth.main.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.it.yousef.dagger2ex.R;
import com.it.yousef.dagger2ex.databinding.PostBindingItemBinding;
import com.it.yousef.dagger2ex.models.Post;

import java.util.ArrayList;
import java.util.List;

public class HomeBindingAdapter extends RecyclerView.Adapter<HomeBindingAdapter.Holder> {

    private Context context;
    private List<Post> posts;

    public HomeBindingAdapter(List<Post> posts,Context context) {
        this.posts = posts;
        this.context=context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostBindingItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(context)
                , R.layout.post_binding_item,parent,false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.binding.setPost(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private PostBindingItemBinding binding;

        public Holder(@NonNull PostBindingItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;


        }
    }
}
