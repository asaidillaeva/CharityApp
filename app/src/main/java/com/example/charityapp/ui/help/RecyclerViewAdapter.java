package com.example.charityapp.ui.help;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.charityapp.R;
import com.example.charityapp.model.Needy;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecyclerViewAdapter extends FirebaseRecyclerAdapter<Needy, RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;


    public RecyclerViewAdapter(@NonNull FirebaseRecyclerOptions<Needy> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int i, @NonNull Needy needy) {

        holder.fullname.setText(needy.getFullName());
        holder.address.setText(needy.getAddress());
        holder.mobile.setText(needy.getPhone());
        holder.need.setText(needy.getNeed());
        holder.time.setText(Utils.DateFormat(needy.getTimestamp().toString()));


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor())
                .error(Utils.getRandomDrawbleColor())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();


        Glide.with(context)
                .load(needy.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.imageView.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);

    }



    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnItemClickListener onItemClickListener;
        TextView fullname, address, need, mobile, time;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);

            fullname = itemView.findViewById(R.id.fullname);
            address = itemView.findViewById(R.id.address);
            need = itemView.findViewById(R.id.needs);
            mobile = itemView.findViewById(R.id.mobile);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.image);

            this.onItemClickListener = onItemClickListener;

        }


        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }


    }
}
